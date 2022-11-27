package bot.external.spreadsheets.questions;

import bot.app.utils.data.questions.Question;
import bot.backend.nodes.restriction.Restriction;
import bot.external.spreadsheets.SpreadSheetUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseQuestionForm<Q extends Question, T> {
    public int id;
    public String question;

    public Function<Object, Restriction<?>> applying;
    public Function<T, Object> parseFunction;

    public Class<T> inputClass;

    public abstract Q getQuestion();
    protected abstract void parseDefinition(List<String> definition);

    public BaseQuestionForm(List<Object> row, Class<T> inputClass) throws NoSuchMethodException {
        List<String> strRow = row.stream().map(e -> (String)e).collect(Collectors.toList());
        this.id = Integer.parseInt(strRow.get(0));
        this.question = strRow.get(1);
        this.inputClass = inputClass;

        String   createPart   = strRow.get(3);
        Method createMethod = SpreadSheetUtils.class.getMethod(createPart, Object.class);
        this.applying = obj -> {
            try {
                return (Restriction<?>) createMethod.invoke(null, obj);
            } catch (Exception e) {
                return null;
            }
        };


        String   parsePart   = strRow.get(4);
        Method   parseMethod = SpreadSheetUtils.class.getMethod(parsePart, inputClass);
        this.parseFunction = s -> {
            try {
                return parseMethod.invoke(null, s);
            } catch (Exception e) {
                return null;
            }
        };
        parseDefinition(strRow.subList(5, strRow.size()));
    }
}
