package bot.external.spreadsheets;

import bot.app.utils.data.questions.Answer;
import bot.app.utils.data.questions.Question;
import bot.backend.nodes.restriction.Restriction;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "bed-project";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/google/tokens";

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/google/credentials.json";

    private static String SHEET_ID_STATIC;

    @Value("${sheetId}")
    public void setNameStatic(String name) {
        SheetsQuickstart.SHEET_ID_STATIC = name;
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static List<Question> getQuestions(SpreadSheetConfig spreadSheetConfig, BiFunction<List<Object>, SpreadSheetConfig, Question> toQuestionFunction) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String range = spreadSheetConfig.getListWithData() + "!" + spreadSheetConfig.getRange();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(SHEET_ID_STATIC, range)
                .execute();
        List<List<Object>> values = response.getValues();
        List<Question> result = new ArrayList<>();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            throw new NoSpreadSheetException();
        } else {
            for (List<Object> row : values) {
                Question q = toQuestionFunction.apply(row, spreadSheetConfig);
                if (q == null) continue;
                result.add(q);
            }
            return result;
        }
    }


    public static List<Question> getQuestions(SpreadSheetConfig spreadSheetConfig) throws GeneralSecurityException, IOException {
        return getQuestions(spreadSheetConfig, (row, ssc) -> {
            if (row.isEmpty()) return null;
            int questionId = Integer.parseInt((String) row.get(0));
            String questionText = (String) row.get(1);
            List<Answer<String>> answers = new ArrayList<>();
            var answersFromTableIterator = row.subList(2, row.size()).iterator();
            while (answersFromTableIterator.hasNext()) {
                answers.add(
                        new Answer<>(
                                (String) answersFromTableIterator.next(),
                                Integer.parseInt((String) answersFromTableIterator.next())
                        )
                );
            }
            Question q = new Question(questionId, questionText, answers, ssc.getInterpreter());
            return q;
        });
    }

    public static List<Question> getQuestions2(SpreadSheetConfig spreadSheetConfig) throws GeneralSecurityException, IOException {
        return getQuestions(spreadSheetConfig, (row, ssc) -> {
            if (row.isEmpty()) return null;
            try {
                RowForm rowForm = new RowForm(row);

                return null;
            } catch (NoSuchMethodException e) {
                return null;
            }
        });
    }

    @Getter
    @Setter
    private static class RowForm {
        public int id;
        public String question;

        public Function<?, Restriction<?>> applying;
        public Function<String, ?> parseFunction;

        public List<AnswerCell> answers;

        public RowForm(List<Object> row) throws NoSuchMethodException {
            List<String> strRow = row.stream().map(e -> (String)e).collect(Collectors.toList());
            this.id = Integer.parseInt(strRow.get(0));
            this.question = strRow.get(1);

            String   createPart   = strRow.get(2);
            Method   createMethod = SpreadSheetUtils.class.getMethod(createPart, Object.class);
            this.applying = obj -> {
                try {
                    return (Restriction<?>) createMethod.invoke(null, obj);
                } catch (Exception e) {
                    return null;
                }
            };


            String   parsePart   = strRow.get(3);
            Method   parseMethod = SpreadSheetUtils.class.getMethod(parsePart, String.class);
            this.parseFunction = s -> {
                try {
                    return parseMethod.invoke(null, s);
                } catch (Exception e) {
                    return null;
                }
            };

            this.answers = strRow.subList(4, strRow.size())
                    .stream()
                    .map(AnswerCell::new)
                    .collect(Collectors.toList());
        }


        @Getter
        @Setter
        public static class AnswerCell {
            public String key;
            public int nextId;

            public AnswerCell(String s) {
                String[] parts = s.split(";");
                this.key = parts[0];
                this.nextId = Integer.parseInt(parts[1]);
            }
        }
    }

    private static class NoSpreadSheetException extends RuntimeException { }
}