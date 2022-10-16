package bot.app.utils.data;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
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

    public List<List<InlineKeyboardButton>> getButtons() {
        List<List<InlineKeyboardButton>> btns = new ArrayList<>();
        int btnIndex = 0;
        for (String answer : answers) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setCallbackData("btn" + (btnIndex++));
            btn.setText(answer);
            btns.add(new ArrayList<>(List.of(btn)));
        }
        InlineKeyboardButton btn = new InlineKeyboardButton();
        btn.setCallbackData("btn-1");
        btn.setText("stop");
        btns.add(new ArrayList<>(List.of(btn)));
        return btns;
    }

}
