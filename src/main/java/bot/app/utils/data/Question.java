package bot.app.utils.data;

import java.util.List;
import java.util.function.Function;

public class Question {
    private final String question;
    private final List<String> answers;
    private final Function<String, DataBlock<?>> interpreter;

    public Question(String question, List<String> answers, Function<String, DataBlock<?>> interpreter) {
        this.question = question;
        this.answers = answers;
        this.interpreter = interpreter;
    }

    public Question(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
        this.interpreter = (String s) -> new DataBlock<String>(question, s);
    }


    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public DataBlock<?> convertAnswer(String answer) {
        return interpreter.apply(answer);
    }
}
