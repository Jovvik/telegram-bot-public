package bot.app.utils.data.questions;

import bot.app.utils.data.DataBlock;

import java.util.List;
import java.util.function.BiFunction;

public abstract class ChangeableQuestion extends Question implements Changeable<Question> {
    public ChangeableQuestion(int id, String question, List<Answer<String>> answers, BiFunction<String, Answer<String>, DataBlock<?>> interpreter) {
        super(id, question, answers, interpreter);
    }

    public ChangeableQuestion(int id, String question, List<Answer<String>> answers) {
        super(id, question, answers);
    }
}
