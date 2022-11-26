package bot.app.utils.data.questions;

import bot.app.TelegramBot;
import bot.app.utils.compressing.BestViewTask;
import bot.app.utils.data.DataBlock;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class SwitchQuestion extends ChangeableQuestion {

    @Getter
    private Set<Integer> chosen = new HashSet<>();

    private Integer messageId;
    public SwitchQuestion(
            int id,
            String question,
            List<Answer<String>> answers,
            Set<Integer> chosen
    ) {
        super(id, question, answers);
        this.chosen = chosen;
    }

    public SwitchQuestion(
            int id,
            String question,
            List<Answer<String>> answers
    ) {
        this(id, question, answers, new HashSet<>());
    }

    @Override
    public Question copy() {
        return new SwitchQuestion(
                id,
                question,
                answers,
                new HashSet<>(chosen)
        );
    }

    @Override
    public List<List<InlineKeyboardButton>> getButtons() {
        List<List<InlineKeyboardButton>> btns = new ArrayList<>();

        List<List<Integer>> bestFit = BestViewTask.fit(answers, question.length());
        int btnRow = 0;
        for (List<Integer> row : bestFit) {
            btns.add(new ArrayList<>());
            for (Integer btnIndex : row) {
                String PREFIX = chosen.contains(btnIndex) ? "\u2705 " : "";
                btns.get(btnRow).add(
                        InlineKeyboardButton.builder()
                                .callbackData("swbtn" + btnIndex)
                                .text(PREFIX + answers.get(btnIndex).getAnswer())
                                .build());
            }
            btnRow++;
        }
        btns.add(List.of(
                InlineKeyboardButton.builder()
                        .callbackData("swbtn-1")
                        .text("- okay -")
                        .build()
                )
        );
        return btns;
    }

    @Override
    public Message send(TelegramBot bot, Long chatId) throws TelegramApiException {
        Message m = super.send(bot, chatId);
        messageId = m.getMessageId();
        return m;
    }

    public void update(TelegramBot bot, Long chatId, int action) throws TelegramApiException {

        if (chosen.contains(action)) {
            chosen.remove(action);
        } else {
            chosen.add(action);
        }

        var changeMarkUp = new EditMessageReplyMarkup();
        changeMarkUp.setChatId(Long.toString(chatId));
        changeMarkUp.setMessageId(messageId);

        var iku = new InlineKeyboardMarkup();
        iku.setKeyboard(getButtons());

        changeMarkUp.setReplyMarkup(iku);
        changeMarkUp.setChatId(Long.toString(chatId));

        bot.execute(changeMarkUp);
    }
}
