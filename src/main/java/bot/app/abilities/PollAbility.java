package bot.app.abilities;

import bot.app.TelegramBot;
import bot.app.utils.data.Question;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.BiConsumer;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;


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

    public Reply buttonCallback() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            var tgbot = (TelegramBot) bot;
            var userId = upd.getCallbackQuery().getFrom().getId();
            int aID = Integer.parseInt(upd.getCallbackQuery().getData().substring("btn".length()));
            var pollService = tgbot.getPollService();
            
            if (!pollService.existUserPollSession(userId)) {
                System.out.printf("User[%s] try to join closed poll%n", userId);
                return;
            }
            if (aID == -1) {
                System.out.printf("User[%s] stop poll after %d questions%n",
                        userId,
                        pollService.getUserPollInfos(userId).size());

                pollService.stopPoll(userId);
                SendMessage sm = new SendMessage();
                sm.setText("thanks for answers!");
                sm.setChatId(Long.toString(getChatId(upd)));
                try {
                    bot.execute(sm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                Question question = pollService.currQuestion(userId);
                String answer = question.getAnswers().get(aID);
                pollService.handleAnswer(userId, question.convertAnswer(answer));

                Question newQuestion = pollService.getQuestionForUser(userId);
                SendMessage sm = new SendMessage();
                sm.setText(newQuestion.getQuestion());
                sm.setChatId(Long.toString(getChatId(upd)));
                InlineKeyboardMarkup rmu = new InlineKeyboardMarkup();
                rmu.setKeyboard(newQuestion.getButtons());
                sm.setReplyMarkup(rmu);
                try {
                    bot.execute(sm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        };

        return Reply.of(
                action,
                Flag.CALLBACK_QUERY,
                upd -> upd.getCallbackQuery().getData().startsWith("btn"));
    }
}
