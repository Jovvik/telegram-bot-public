package bot.app;

import bot.app.abilities.*;
import bot.app.service.EventBuilderService;
import bot.app.service.PollService;
import bot.app.service.QuestionDataBase;
import bot.backend.nodes.results.TimeTable;
import bot.external.spreadsheets.SpreadSheetConfig;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class TelegramBot extends AbilityBot {

    private static final String abilitiesPath = "bot/app/abilities";

    @Setter
    private EventBuilderService eventBuilderService;

    @Setter
    private PollService pollService;

    public TelegramBot(String botToken, String botUsername) {
        super(botToken, botUsername);
        addAllExtensions();
    }

    private void addAllExtensions() {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(ClassLoader
                                .getSystemClassLoader()
                                .getResourceAsStream(abilitiesPath))
                )
        );
        addExtensions(br.lines()
                .filter(s -> s.endsWith("Ability.class"))
                .map(s -> abilitiesPath.replaceAll("/", "\\.") + "." + s.split("\\.")[0])
                .map(s -> {
                    try {
                        return (AbilityTemplate)
                                Class.forName(s)
                                        .getConstructor(TelegramBot.class)
                                        .newInstance(this);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
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
        super.onUpdateReceived(update);
    }


    public PollService getPollService() {
        return pollService;
    }

    public EventBuilderService getEventBuilderService() {
        return eventBuilderService;
    }

    public void notifyUser(TimeTable timeTable, Long userId) {
        var eventService = getEventBuilderService();
        System.out.printf("User[%s] check status of results%n", userId);
        if (eventService.isEventDone(userId)) {
            var plan = eventService.getResult(userId);
            String planStr = plan == null
                    ? "К сожалению, ничего не получилось("
                    : plan.toString();

            SendMessage sm = new SendMessage();
            sm.setChatId(Long.toString(userId));
            sm.setText(planStr);
            sm.setParseMode("Markdown");
            try {
                Message m = execute(sm);
                if (plan != null) {
                    var req = plan.createMap();
                    HttpGet request = new HttpGet(req);
                    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

                    request.addHeader("content-type", "image/png");

                    int fileId = new Random().nextInt();
                    String fileName = "download-image-" + fileId + ".png";
                    File file = new File(fileName);

                    try (CloseableHttpResponse response = httpClient.execute(request)) {
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            try (FileOutputStream outstream = new FileOutputStream(file)) {
                                entity.writeTo(outstream);
                            }
                        }
                        SendPhoto sendPhoto = new SendPhoto();
                        sendPhoto.setChatId(Long.toString(userId));
                        sendPhoto.setPhoto(new InputFile().setMedia(file));
                        sendPhoto.setReplyToMessageId(m.getMessageId());

                        try {
                            execute(sendPhoto);
                            file.delete();
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
