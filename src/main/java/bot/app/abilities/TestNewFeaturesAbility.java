package bot.app.abilities;

import bot.app.TelegramBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class TestNewFeaturesAbility extends AbilityTemplate{
    public TestNewFeaturesAbility(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Ability create() {
        return Ability.builder()
                .name("test")
                .privacy(Privacy.CREATOR)
                .locality(Locality.ALL)
                .action(messageContext -> {
                    String question = "Who are you?";
                    List<KeyboardRow> buttonsTable = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        buttonsTable.add(new KeyboardRow());
                        for (int j = 0; j < 3; j++) {
                            KeyboardButton button = new KeyboardButton();
                            String answer = String.format("b%d", i * 3 + j);
                            button.setText(answer);
                            buttonsTable.get(i).add(button);
                        }
                    }
                    var sm = new SendMessage();
                    sm.setText(question);
                    sm.setChatId(Long.toString(messageContext.chatId()));
                    var rmu = new ReplyKeyboardMarkup();
                    rmu.setKeyboard(buttonsTable);
                    sm.setReplyMarkup(new ReplyKeyboardRemove(true));
                    try {
                        bot.execute(sm);
                    } catch (TelegramApiException e) {
                        System.err.println("Problem: " + e.getMessage());
                        e.printStackTrace(System.err);
                    }
                })
                .build();
    }
}
