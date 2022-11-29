package bot.external.spreadsheets.questions;

import bot.app.utils.data.questions.QuestionResult;
import bot.app.utils.data.questions.SliderQuestion;
import bot.backend.nodes.categories.Category;
import bot.external.spreadsheets.SpreadSheetUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BiFunction;

public class SliderQuestionForm extends BaseQuestionForm<SliderQuestion, String> {

    public String down;
    public String up;
    public String defaultValue;
    public int nextId;
    public BiFunction<String, String, String> changeFunction;


    public SliderQuestionForm(List<Object> row) throws NoSuchMethodException {
        super(row, String.class);
    }

    @Override
    public SliderQuestion getQuestion() {
        return new SliderQuestion(
                id,
                question,
                down,
                defaultValue,
                up,
                nextId,
                changeFunction,
                (question, answer) -> new QuestionResult(
                        question,
                        Category.DEFAULT,
                        List.of(answer),
                        applying.apply(parseFunction.apply(answer.getAnswer()))
                )
        );
    }

    @Override
    protected void parseDefinition(List<String> definition) {
        this.down = definition.get(0);
        this.defaultValue = definition.get(1);
        this.up = definition.get(2);

        try {
            Method change = SpreadSheetUtils.class.getMethod(definition.get(3), String.class, String.class);

            this.changeFunction = (dv, s1) -> {
                try {
                    return (String) change.invoke(null, dv, s1);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    return null;
                }
            };

        } catch (Exception e) {
            return;
        }

        this.nextId = Integer.parseInt(definition.get(4));
    }
}
