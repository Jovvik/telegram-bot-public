package bot.app.abilities;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;

public class HelloAbility extends AbilityTemplate {
    public HelloAbility(AbilityBot bot) {
        super(bot);
    }

    @Override
    public Ability create() {
        return Ability.builder()
                .name("hello")
                .info("say hello")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(messageContext -> {
                    bot.silent().send("Sir, I have gone left.", messageContext.chatId());
                })
                .build();
    }
}
