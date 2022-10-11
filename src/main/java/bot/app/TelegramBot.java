package bot.app;

import bot.app.abilities.AnswerWithButtonsAbility;
import bot.app.abilities.HelloAbility;
import bot.app.utils.ButtonMessage;
import bot.app.utils.StringSerialization;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.List;

public class TelegramBot extends AbilityBot {

    public TelegramBot(String botToken, String botUsername) {
        super(botToken, botUsername);
        addExtensions(
                new HelloAbility(this),
                new AnswerWithButtonsAbility(this)
        );
    }

    @Override
    public long creatorId() {
        return 420953808L;
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public void onClosing() {
        super.onClosing();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            try {
                Object data = StringSerialization.fromString(update.getCallbackQuery().getData(), ButtonMessage.class);
                ButtonMessage buttonMessage = ((ButtonMessage) data);
                String msg = String.format("%s %s!", buttonMessage.getQuestion(), buttonMessage.getAnswer());
                silent().send(msg, update.getCallbackQuery().getMessage().getChatId());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            super.onUpdateReceived(update);
        }
    }
}
