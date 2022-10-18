package bot.app.abilities;

import bot.app.TelegramBot;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.util.AbilityExtension;

public abstract class AbilityTemplate implements AbilityExtension {
    protected TelegramBot bot;
    public abstract Ability create();

    public AbilityTemplate(TelegramBot bot) {
        this.bot = bot;
    }
}
