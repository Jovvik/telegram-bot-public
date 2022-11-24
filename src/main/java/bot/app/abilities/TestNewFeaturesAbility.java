package bot.app.abilities;

import bot.app.TelegramBot;
import bot.app.utils.data.questions.ChangeableQuestion;
import bot.app.utils.data.questions.Question;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class TestNewFeaturesAbility extends AbilityTemplate{
    public TestNewFeaturesAbility(TelegramBot bot) {
        super(bot);
    }

    ChangeableQuestion q;

    @Override
    public Ability create() {
        return Ability.builder()
                .name("test")
                .privacy(Privacy.CREATOR)
                .locality(Locality.ALL)
                .input(0)
                .action(messageContext -> {
                    q = new ChangeableQuestion(
                            "Time?",
                            "13:00",
                            "+30m",
                            "-30m",
                            (c, act) -> {
                                try {
                                    String[] parts = c.split(":");
                                    int h = Integer.parseInt(parts[0]);
                                    int m = Integer.parseInt(parts[1]);
                                    int time = (h * 60 + m + (act.equals("+30m") ? 30 : -30) + 60 * 24) % (60 * 24);
                                    h = time / 60;
                                    m = time % 60;
                                    return (h < 10
                                            ? "0" + h
                                            : Integer.toString(h))
                                            + ":"
                                            + ((m == 0) ? "00" : "30");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                return null;
                            }
                    );
                    try {
                        q.send(bot, messageContext.chatId());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();
    }

    public Reply buttonCallback() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            var tgbot = (TelegramBot) bot;
            var userId = upd.getCallbackQuery().getFrom().getId();
            var chatId = getChatId(upd);
            int act = Integer.parseInt(upd.getCallbackQuery().getData().substring("chbtn".length()));

            if (act == 1) {
                var sm = new SendMessage();
                sm.setText("Okay! " + q.getResult());
                sm.setChatId(Long.toString(userId));
                try {
                    bot.execute(sm);
                } catch (TelegramApiException e) {
                    e.printStackTrace(System.out);
                }
            } else {
                try {
                    q.update(tgbot, chatId, act);
                } catch (TelegramApiException e) {
                    e.printStackTrace(System.err);
                }
            }

        };

        return Reply.of(
                action,
                Flag.CALLBACK_QUERY,
                upd -> upd.getCallbackQuery().getData().startsWith("chbtn"));
    }

}
