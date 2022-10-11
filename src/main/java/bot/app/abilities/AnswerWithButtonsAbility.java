package bot.app.abilities;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class AnswerWithButtonsAbility extends AbilityTemplate{
    public AnswerWithButtonsAbility(AbilityBot bot) {
        super(bot);
    }

    @Override
    public Ability create() {
        return Ability.builder()
                .name("ask")
                .info("ask with buttons")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(messageContext -> {
                    List<List<InlineKeyboardButton>> buttonsTable = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        buttonsTable.add(new ArrayList<>());
                        for (int j = 0; j < 3; j++) {
                            InlineKeyboardButton button = new InlineKeyboardButton();
                            button.setText(String.format("b%d", i * 3 + j));
                            button.setCallbackData("button " + (i * 3 + j) + " pressed");
                            buttonsTable.get(i).add(button);
                        }
                    }
                    var sm = new SendMessage();
                    sm.setText("da");
                    sm.setChatId(Long.toString(messageContext.chatId()));
                    var rmu = new InlineKeyboardMarkup();
                    rmu.setKeyboard(buttonsTable);
                    sm.setReplyMarkup(rmu);
                    try {
                        bot.execute(sm);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                })
                .build();
    }
}
