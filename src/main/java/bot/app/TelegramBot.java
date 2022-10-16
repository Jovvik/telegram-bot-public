package bot.app;

import bot.app.abilities.AnswerWithButtonsAbility;
import bot.app.abilities.HelloAbility;
import bot.app.abilities.PollAbility;
import bot.app.service.EventBuilderService;
import bot.app.service.PollService;
import bot.app.service.QuestionDataBase;
import bot.app.utils.Message;
import bot.app.utils.data.ButtonInfo;
import bot.app.utils.StringSerialization;
import bot.app.utils.data.Question;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class TelegramBot extends AbilityBot {

    private final PollService pollService = new PollService(
            new QuestionDataBase(List.of("a", "b")),
            new EventBuilderService()
    );

    public TelegramBot(String botToken, String botUsername) {
        super(botToken, botUsername);
        addExtensions(
                new HelloAbility(this),
                new AnswerWithButtonsAbility(this),
                new PollAbility(this)
        );
    }

    @Override
    public long creatorId() {
        return 420953808L;
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public void onClosing() {
        super.onClosing();
    }

    public PollService getPollService() {
        return pollService;
    }

    public Reply sayYuckOnImage() {
        // getChatId is a public utility function in rg.telegram.abilitybots.api.util.AbilityUtils
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> bot.silent().send("Yuck", getChatId(upd));

        return Reply.of(action, Flag.PHOTO);
    }

    public Reply buttonCallback() {
        // getChatId is a public utility function in rg.telegram.abilitybots.api.util.AbilityUtils
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            var tgbot = (TelegramBot) bot;
            var userId = upd.getCallbackQuery().getFrom().getId();
            int aID = Integer.parseInt(upd.getCallbackQuery().getData().substring("btn".length()));
            if (aID == -1) {
                System.out.println(
                        String.format("User[%s] stop poll after %d questions",
                                userId,
                                tgbot.pollService.getUserPollInfos(userId).size())
                );
                tgbot.pollService.stopPoll(userId);
                SendMessage sm = new SendMessage();
                sm.setText("thanks for answers!");
                sm.setChatId(Long.toString(getChatId(upd)));
                try {
                    bot.execute(sm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                Question question = tgbot.pollService.currQuestion(userId);
                String answer = question.getAnswers().get(aID);
                tgbot.pollService.handleAnswer(userId, question.convertAnswer(answer));

                Question newQuestion = tgbot.pollService.getQuestionForUser(userId);
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
                upd -> {
                    return upd.getCallbackQuery().getData().startsWith("btn");
                });
    }

    @Override
    public void onUpdateReceived(Update update) {
//        if (update.hasCallbackQuery()) {
//            try {
//                Object data = Message.decompose(update.getCallbackQuery().getData());
//                if (data instanceof ButtonInfo) {
//                    ButtonInfo buttonInfo = ((ButtonInfo) data);
//                    String msg = String.format("%s %s!", buttonInfo.getQuestion(), buttonInfo.getAnswer());
//                    silent().send(msg, update.getCallbackQuery().getMessage().getChatId());
//                }
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//            }
//        } else {
        super.onUpdateReceived(update);
//        }
    }

}
