package bot.app;

import bot.app.service.EventBuilderService;
import bot.app.service.PollService;
import bot.app.service.QuestionDataBase;
import bot.external.graphviz.Vizualization;
import bot.external.spreadsheets.SheetsService;
import bot.external.spreadsheets.SpreadSheetConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class BotStart implements CommandLineRunner {

    @Value("${telegramToken}")
    private String token;

    @Value("${name}")
    private String name;

    @Configuration
    public static class TelegramBotConfiguration {
        @Bean
        PollService pollService(QuestionDataBase questionDataBase, EventBuilderService eventBuilderService) {
            return new PollService(questionDataBase, eventBuilderService);
        }

        @Bean
        QuestionDataBase questionDataBase(SheetsService sheetsService) {
            return new QuestionDataBase(
                    sheetsService,
                    Arrays.asList(SpreadSheetConfig.values())
            );
        }
    }

    @Autowired
    private EventBuilderService eventBuilderService;

    @Autowired
    private PollService pollService;

    @Override
    public void run(String... args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        var bot = new TelegramBot(token, name);

        bot.setPollService(pollService);
        bot.setEventBuilderService(eventBuilderService);
        bot.getEventBuilderService().setTelegramBot(bot);

        botsApi.registerBot(bot);
        bot.execute(SetMyCommands.builder()
                .commands(
                        bot.abilities().values().stream()
                                .filter(ability -> ability.privacy() == Privacy.PUBLIC)
                                .filter(ability -> ability.info() != null)
                                .map(ability -> new BotCommand(ability.name(), ability.info()))
                                .collect(Collectors.toList())
                )
                .build()
        );
        System.out.println(Vizualization.vizulize(bot.getPollService().getQuestionDataBase()));
    }
}
