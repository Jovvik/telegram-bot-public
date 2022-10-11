package bot.app;

import bot.app.abilities.AnswerWithButtonsAbility;
import bot.app.abilities.HelloAbility;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;

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
            silent().send(update.getCallbackQuery().getData(), update.getCallbackQuery().getMessage().getChatId());
        } else {
            super.onUpdateReceived(update);
        }
    }
}
