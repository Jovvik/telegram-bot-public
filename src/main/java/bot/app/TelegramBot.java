package bot.app;

import bot.app.abilities.*;
import bot.app.service.EventBuilderService;
import bot.app.service.PollService;
import bot.app.service.QuestionDataBase;
import bot.external.spreadsheets.SpreadSheetConfig;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TelegramBot extends AbilityBot {
    private static final String abilitiesPath = "bot/app/abilities";

    private final EventBuilderService eventBuilderService = new EventBuilderService();
    private final PollService pollService = new PollService(
            new QuestionDataBase(
                    Arrays.asList(SpreadSheetConfig.values())
            ),
            eventBuilderService
    );

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

}
