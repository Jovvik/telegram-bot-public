package bot.app;

import bot.app.abilities.TestNewFeaturesAbility;
import bot.app.abilities.HelloAbility;
import bot.app.abilities.PollAbility;
import bot.app.service.EventBuilderService;
import bot.app.service.PollService;
import bot.app.service.QuestionDataBase;
import bot.app.utils.data.Question;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.function.BiConsumer;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class TelegramBot extends AbilityBot {

    private final PollService pollService = new PollService(
            new QuestionDataBase(List.of("a", "b")),
            new EventBuilderService()
    );

    public TelegramBot(String botToken, String botUsername) {
        super(botToken, botUsername);
        addExtensions(
                new HelloAbility(this),
                new TestNewFeaturesAbility(this),
                new PollAbility(this)
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

    public PollService getPollService() {
        return pollService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        super.onUpdateReceived(update);
    }

}
