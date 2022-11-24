package bot.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.stream.Collectors;

@Component
public class BotStart implements CommandLineRunner {

    @Value("${telegramToken}")
    private String token;

    @Value("${name}")
    private String name;

    @Override
    public void run(String... args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        var bot = new TelegramBot(token, name);
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
    }
}
