package bot.app;

import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            String token;
            String name;

            try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
                Properties properties = new Properties();
                properties.load(input);

                token = properties.getProperty("token");
                name = properties.getProperty("name");

            } catch (Exception e) {
                System.err.println(e.getMessage());
                return;
            }

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

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
