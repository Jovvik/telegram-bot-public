package bot.app.utils.data.questions;

import bot.app.TelegramBot;
import bot.app.utils.data.DataBlock;
import bot.backend.nodes.restriction.Restriction;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SliderQuestion extends ChangeableQuestion {
    private String upString;
    private String downString;
    private String currValue;

    private BiFunction<String, String, String> changeFunction;

    private Integer messageId;

    public SliderQuestion(
            String question,
            String defaultValue,
            String upString,
            String downString,
            BiFunction<String, String, String> changeFunction,
            BiFunction<String, Answer<String>, DataBlock<?>> interpreter) {
        super(239, question, List.of(new Answer<>(defaultValue, -239)), interpreter);
        this.currValue = defaultValue;
        this.upString = upString;
        this.downString = downString;
        this.changeFunction = changeFunction;
    }

    public SliderQuestion(String question, String defaultValue, String upString,
                          String downString, BiFunction<String, String, String> changeFunction) {
        this(question, defaultValue, upString, downString, changeFunction, DataBlock::new);
    }

    public SliderQuestion(String question, String defaultValue, String upString,
                          String downString, BiFunction<String, String, String> changeFunction,
                          Function<Answer<String>, Restriction<?>> restrict) {
        this(question, defaultValue, upString, downString, changeFunction, DataBlock::new);
        this.restrict = restrict;
    }


    @Override
    public Message send(TelegramBot bot, Long chatId) throws TelegramApiException {
        Message m = super.send(bot, chatId);
        messageId = m.getMessageId();
        return m;
    }

    @Override
    public List<List<InlineKeyboardButton>> getButtons() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton minusOne = new InlineKeyboardButton();
        minusOne.setText(downString);
        minusOne.setCallbackData("chbtn0");

        InlineKeyboardButton mid = new InlineKeyboardButton();
        mid.setText(currValue);
        mid.setCallbackData("chbtn1");

        InlineKeyboardButton plusOne = new InlineKeyboardButton();
        plusOne.setText(upString);
        plusOne.setCallbackData("chbtn2");

        row.add(minusOne);
        row.add(mid);
        row.add(plusOne);

        buttons.add(row);

        return buttons;
    }

    @Override
    public List<Answer<String>> getAnswers() {
        return super.getAnswers();
    }

    public String getAction(int action) {
        if (action == 0) return downString;
        return upString;
    }

    public String getResult() { return currValue;}

    public void update(TelegramBot bot, Long chatId, int action) throws TelegramApiException {
        currValue = changeFunction.apply(currValue, getAction(action));
        var changeMarkUp = new EditMessageReplyMarkup();
        changeMarkUp.setChatId(Long.toString(chatId));
        changeMarkUp.setMessageId(messageId);

        var iku = new InlineKeyboardMarkup();
        iku.setKeyboard(getButtons());

        changeMarkUp.setReplyMarkup(iku);
        changeMarkUp.setChatId(Long.toString(chatId));

        bot.execute(changeMarkUp);
    }

    public SliderQuestion copy() {
        return new SliderQuestion(
                question,
                currValue,
                upString,
                downString,
                changeFunction,
                interpreter
        );
    }
}
