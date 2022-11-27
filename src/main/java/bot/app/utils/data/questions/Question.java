package bot.app.utils.data.questions;

import bot.app.TelegramBot;
import bot.app.utils.compressing.BestViewTask;
import bot.app.utils.data.DataBlock;
import bot.backend.nodes.restriction.Restriction;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Question {
    @Getter
    protected final int id;
    @Getter
    protected final String question;
    @Getter
    protected final List<Answer<String>> answers;
    protected final BiFunction<String, Answer<String>, DataBlock<?>> interpreter;

    @Getter
    protected Function<Answer<String>, Restriction<?>> restrict;

    public Question(int id, String question, List<Answer<String>> answers, BiFunction<String, Answer<String>, DataBlock<?>> interpreter) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.interpreter = interpreter;
    }

    public Question(int id, String question, List<Answer<String>> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.interpreter = DataBlock::new;
    }

    public Question(int id, String question, List<Answer<String>> answers, Function<Answer<String>, Restriction<?>> restrict) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.restrict = restrict;
        this.interpreter = DataBlock::new;
    }

    public DataBlock<?> convertAnswer(Answer<String> answer) {
        return interpreter.apply(this.question, answer);
    }

    public List<List<InlineKeyboardButton>> getButtons() {
        List<List<InlineKeyboardButton>> btns = new ArrayList<>();

        List<List<Integer>> bestFit = BestViewTask.fit(answers, question.length());
        int btnRow = 0;
        for (List<Integer> row : bestFit) {
            btns.add(new ArrayList<>());
            for (Integer btnIndex : row) {
                btns.get(btnRow).add(
                        InlineKeyboardButton.builder()
                                .callbackData("btn" + btnIndex)
                                .text(answers.get(btnIndex).getAnswer())
                                .build());
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
