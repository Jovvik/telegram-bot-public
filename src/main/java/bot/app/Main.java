package bot.app;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            String token = "";
            String name = "";

            try (InputStream input = new FileInputStream("src/main/resources/bot.properties")) {
                Properties properties = new Properties();
                properties.load(input);

                token = properties.getProperty("token");
                name = properties.getProperty("name");

            } catch (Exception e) {
                System.err.println(e.getMessage());
                return;
            }
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot(token, name));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
