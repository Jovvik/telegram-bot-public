package bot.app.abilities;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.util.AbilityExtension;

public abstract class AbilityTemplate implements AbilityExtension {
    protected AbilityBot bot;
    public abstract Ability create();

    public AbilityTemplate(AbilityBot bot) {
        this.bot = bot;
    }
}
