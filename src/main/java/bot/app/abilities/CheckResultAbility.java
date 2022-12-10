package bot.app.abilities;

import bot.app.TelegramBot;
import bot.app.service.EventBuilderService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class CheckResultAbility extends AbilityTemplate {
    public CheckResultAbility(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Ability create() {
        return Ability.builder()
                .name("check")
                .info("check results")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .input(0)
                .action(messageContext -> {
                    var eventService = bot.getEventBuilderService();
                    var userId = messageContext.user().getId();
                    SendMessage sm = new SendMessage();
                    sm.setChatId(Long.toString(getChatId(messageContext.update())));
                    int userPosition = 1 + eventService.queuePosition(userId);
                    if (userPosition == 0) {
                        sm.setText("Your event is creating right *now*, please wait a little bit!");
                    } else {
                        sm.setText("Your request in queue, before you *" + (userPosition - 1) + "* people.");
                    }
                    sm.enableMarkdown(true);
                    try {
                        bot.execute(sm);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                })
                .build();
    }
}
