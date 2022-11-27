package bot.app.abilities;

import bot.app.TelegramBot;
import bot.app.utils.data.questions.Answer;
import bot.app.utils.data.questions.Question;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.Update;
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
                .input(0)
                .action(messageContext -> {
                    try {
                        askQuestion(bot, messageContext.chatId(), messageContext.user().getId());
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                })
                .build();
    }

    public Reply buttonCallback() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            var tgbot = (TelegramBot) bot;
            var userId = upd.getCallbackQuery().getFrom().getId();
            var chatId = getChatId(upd);
            int aID = Integer.parseInt(upd.getCallbackQuery().getData().substring("btn".length()));
            var pollService = tgbot.getPollService();

            if (!pollService.existUserPollSession(userId)) {
                System.out.printf("User[%s] try to join closed poll%n", userId);
                return;
            }

            Question question = pollService.currQuestion(userId);
            Answer<String> answer = question.getAnswers().get(aID);
            pollService.handleAnswer(userId, question.convertAnswer(answer));
            if (!pollService.hasNextQuestion(userId)) {
                StopPollAbility.stopPoll(tgbot, userId, chatId, pollService);
                return;
            }
            askQuestion(tgbot, chatId, userId);
        };

        return Reply.of(
                action,
                Flag.CALLBACK_QUERY,
                upd -> upd.getCallbackQuery().getData().startsWith("btn"));
    }

    private void askQuestion(TelegramBot bot, Long chatId, Long userId) {
        Question newQuestion = bot.getPollService().getQuestionForUser(userId);
        try {
            newQuestion.send(bot, chatId);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
