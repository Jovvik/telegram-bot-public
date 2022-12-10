package bot.external.spreadsheets;

import bot.app.utils.data.questions.BaseQuestion;
import bot.external.spreadsheets.questions.ChooseQuestionForm;
import bot.external.spreadsheets.questions.QuestionType;
import bot.external.spreadsheets.questions.SelectQuestionForm;
import bot.external.spreadsheets.questions.SliderQuestionForm;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

@Service
public class SheetsService {
    private static final String APPLICATION_NAME = "bed-project";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/google/tokens";

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/google/credentials.json";

    @Value("${sheetId}")
    private String SHEET_ID_STATIC;


    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
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

    private List<BaseQuestion<?>> getQuestions(
            SpreadSheetConfig spreadSheetConfig,
            BiFunction<List<Object>, SpreadSheetConfig, BaseQuestion<?>> toQuestionFunction
    ) throws GeneralSecurityException, IOException {
        List<List<Object>> values = cells(spreadSheetConfig);
        List<BaseQuestion<?>> result = new ArrayList<>();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            throw new NoSpreadSheetException();
        } else {
            for (List<Object> row : values) {
                BaseQuestion<?> q = toQuestionFunction.apply(row, spreadSheetConfig);
                if (q == null) continue;
                result.add(q);
            }
            return result;
        }
    }

    public List<List<Object>> cells(SpreadSheetConfig spreadSheetConfig) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String range = spreadSheetConfig.getListWithData() + "!" + spreadSheetConfig.getRange();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(SHEET_ID_STATIC, range)
                .execute();
        return response.getValues();
    }

    public List<BaseQuestion<?>> getQuestions(SpreadSheetConfig spreadSheetConfig) throws GeneralSecurityException, IOException {
        return getQuestions(spreadSheetConfig, (row, ssc) -> {
            if (row.isEmpty()) return null;
            try {
                if (row.size() >= 2 && (row.get(1) instanceof String)) {
                    QuestionType qt = QuestionType.valueOf((String) row.get(1));
                    return switch (qt) {
                        case Choose -> new ChooseQuestionForm(row).getQuestion();
                        case Slider -> new SliderQuestionForm(row).getQuestion();
                        case Select -> new SelectQuestionForm(row).getQuestion();
                        default -> null;
                    };
                }
            } catch (NoSuchMethodException e) {
                return null;
            }
            return null;
        });
    }

    private static class NoSpreadSheetException extends RuntimeException {}
}