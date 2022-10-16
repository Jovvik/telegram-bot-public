package bot.app.abilities;

import bot.app.TelegramBot;
import bot.app.utils.data.Question;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class PollAbility extends AbilityTemplate {
    public PollAbility(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Ability create() {
        return Ability.builder()
                .name("poll")
                .info("start poll with questions")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(messageContext -> {
                    Question question = bot.getPollService().getQuestionForUser(messageContext.user().getId());
                    SendMessage sm = new SendMessage();
                    sm.setText(question.getQuestion());
                    sm.setChatId(Long.toString(messageContext.chatId()));
                    InlineKeyboardMarkup rmu = new InlineKeyboardMarkup();
                    rmu.setKeyboard(question.getButtons());
                    sm.setReplyMarkup(rmu);
                    try {
                        bot.execute(sm);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }
}
