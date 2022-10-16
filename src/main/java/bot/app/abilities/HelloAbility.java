package bot.app.abilities;

import bot.app.TelegramBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HelloAbility extends AbilityTemplate {
    public HelloAbility(TelegramBot bot) {
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
                    try {
                        var sticker = new SendSticker();
                        sticker.setChatId(Long.toString(messageContext.chatId()));
                        sticker.setSticker(new InputFile("CAACAgIAAxkBAAETXMZiZs1nFPnYsreR_QKB7RnI31A8bgACFgAD0D4XGf8UUwVG7Q9WJAQ"));
                        bot.execute(sticker);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();
    }
}
