package bot.external.spreadsheets.questions;

import bot.app.utils.data.questions.BaseQuestion;
import bot.backend.nodes.restriction.Restriction;
import bot.external.spreadsheets.SpreadSheetUtils;

import java.lang.reflect.Method;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseQuestionForm<Q extends BaseQuestion<?>, T> {
    public int id;
    public String question;

    public Function<Object, Restriction<?, ?>> applying;
    public Function<T, Object> parseFunction;

    public Class<T> inputClass;

    public abstract Q getQuestion();

    protected abstract void parseDefinition(List<String> definition);

    public BaseQuestionForm(List<Object> row, Class<T> inputClass) {
        QuestionRow questionRow = QuestionRow.from(row);
        this.id = questionRow.number;
        this.question = questionRow.question;
        this.inputClass = inputClass;

        String createPart = questionRow.createMethodName;
        Method createMethod;
        try {
            createMethod = SpreadSheetUtils.class.getMethod(createPart, Object.class);
            this.applying = obj -> {
                try {
                    return (Restriction<?, ?>) createMethod.invoke(null, obj);
                } catch (Exception e) {
                    return null;
                }
            };
        } catch (NoSuchMethodException ignored) {
            throw new IllegalArgumentException("no such method: " + createPart);
        } // ignore


        String parsePart = questionRow.parseMethod;
        Method parseMethod;
        try {
            parseMethod = SpreadSheetUtils.class.getMethod(parsePart, inputClass);
            this.parseFunction = s -> {
                try {
                    return parseMethod.invoke(null, s);
                } catch (Exception e) {
                    return null;
                }
            };
        } catch (NoSuchMethodException ignored) {
            throw new IllegalArgumentException("no such method: " + parsePart);
        } // ignore
        parseDefinition(questionRow.definition);
    }

    public static class QuestionRow {
        int number;
        QuestionType type;
        String question;
        String createMethodName;
        String parseMethod;
        List<String> definition;

        QuestionRow() {}

        static QuestionRow from(List<Object> row) {
            try {
                if (row.size() >= 6 &&
                    row.stream().allMatch(obj -> obj instanceof String)) {
                    QuestionRow q = new QuestionRow();
                    q.number = Integer.parseInt((String) row.get(0));
                    q.type = QuestionType.valueOf((String) row.get(1));
                    q.question = (String) row.get(2);
                    q.createMethodName = (String) row.get(3);
                    q.parseMethod = (String) row.get(4);
                    q.definition = row.stream()
                            .map(obj -> (String) obj)
                            .collect(Collectors.toList())
                            .subList(5, row.size());
                    return q;
                }
            } catch (Exception ignore) {}
            throw new IllegalArgumentException("wrong format of row: " + row);
        }
    }
}
