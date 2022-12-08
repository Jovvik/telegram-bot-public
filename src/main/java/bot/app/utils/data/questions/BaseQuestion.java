package bot.app.utils.data.questions;

import bot.app.TelegramBot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public abstract class BaseQuestion<T> {

    protected static String BUTTON_PREFIX = "btn";

    public int id;
    public String question;
    public List<Answer<String>> answers;
    public BiFunction<String, T, QuestionResult> resultBiFunction;

    // returns null if answer is not final
    public abstract QuestionResult handlePressing(TelegramBot tgBot, Long chatId, int answerNumber);
    public abstract String createButtonText(int answerNumber);

    protected abstract AnswerOrder answerOrder();

    public List<List<InlineKeyboardButton>> getButtons() {
        List<List<InlineKeyboardButton>> btns = new ArrayList<>();

        List<List<Integer>> bestFit = answerOrder().fit(
                answers.stream()
                        .map(Answer::getAnswer)
                        .collect(Collectors.toList()),
                question
        );
        int btnRow = 0;
        for (List<Integer> row : bestFit) {
            btns.add(new ArrayList<>());
            for (Integer btnIndex : row) {
                btns.get(btnRow).add(InlineKeyboardButton.builder()
                        .callbackData(callBackString(btnIndex))
                        .text(createButtonText(btnIndex))
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

    private String callBackString(int answerNumber) {
        return BUTTON_PREFIX + "-" + id + "-" + answerNumber;
    }


}
