package bot.app.utils.data.questions;

import bot.app.TelegramBot;
import bot.app.utils.compressing.BestViewTask;
import bot.app.utils.data.DataBlock;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Question {
    private final String question;
    protected final List<String> answers;
    private final BiFunction<String, String, DataBlock<?>> interpreter;

    public Question(String question, List<String> answers, BiFunction<String, String, DataBlock<?>> interpreter) {
        this.question = question;
        this.answers = answers;
        this.interpreter = interpreter;
    }

    public Question(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
        this.interpreter = DataBlock::new;
    }


    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public DataBlock<?> convertAnswer(String answer) {
        return interpreter.apply(this.question, answer);
    }

    public List<List<InlineKeyboardButton>> getButtons() {
        List<List<InlineKeyboardButton>> btns = new ArrayList<>();

        List<List<Integer>> bestFit = BestViewTask.fit(answers, question.length());
        int btnRow = 0;
        for (List<Integer> row : bestFit) {
            btns.add(new ArrayList<>());
            for (Integer btnIndex: row) {
                InlineKeyboardButton btn = new InlineKeyboardButton();
                btn.setCallbackData("btn" + btnIndex);
                btn.setText(answers.get(btnIndex));
                btns.get(btnRow).add(btn);
            }
            btnRow++;
        }
        return btns;
    }

    public Message send(TelegramBot bot, Long chatId) throws TelegramApiException {
        SendMessage sm = new SendMessage();
        sm.setText(getQuestion());
        sm.setChatId(Long.toString(chatId));
        InlineKeyboardMarkup rmu = new InlineKeyboardMarkup();
        rmu.setKeyboard(getButtons());
        sm.setReplyMarkup(rmu);
        return bot.execute(sm);
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers.toString() +
                '}';
    }
}
