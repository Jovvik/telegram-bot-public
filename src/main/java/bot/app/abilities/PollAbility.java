package bot.app.abilities;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.Collectors;

public class PollAbility extends AbilityTemplate {
    public PollAbility(AbilityBot bot) {
        super(bot);
    }

    @Override
    public Ability create() {
        return Ability.builder()
                .name("poll")
                .info("start poll with questions")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(messageContext -> {
                    SendMessage sm = new SendMessage();
                    sm.setText(String.join(", ", bot.abilities().keySet()));
                    sm.setChatId(Long.toString(messageContext.chatId()));
                    try {
                        bot.execute(sm);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
//                .reply((bt, msg) -> {bot.silent().send("reply to ", msg.getChatMember().getChat().getId());}, upd -> upd.hasMessage())
                .build();
    }
}
