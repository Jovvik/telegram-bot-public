package bot.app.abilities;

import bot.app.TelegramBot;
import bot.app.service.PollService;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class StopPollAbility extends AbilityTemplate {
    public StopPollAbility(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Ability create() {
        return Ability.builder()
                .name("stop")
                .info("stop active poll")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .input(0)
                .action(messageContext -> {
                    var tgbot = bot;
                    var userId = messageContext.user().getId();
                    var chatId = getChatId(messageContext.update());
                    var pollService = tgbot.getPollService();

                    stopPoll(tgbot, userId, chatId, pollService);
                })
                .build();
    }

    static void stopPoll(TelegramBot tgbot, Long userId, Long chatId, PollService pollService) {
        if (!pollService.existUserPollSession(userId)) {
            System.out.printf("User[%s] try to stop nonexistent poll%n", userId);
            return;
        }
        System.out.printf("User[%s] stop poll after %d questions%n",
                userId,
                pollService.getUserPollInfos(userId).size());

        pollService.stopPoll(userId);
        SendMessage sm = new SendMessage();
        sm.setText("*Спасибо за ответы*!\n" +
                "Ожидайте когда сервис создаст мероприятие специального для вас, " +
                "чтобы проверить готовность - нажмите `/check`!");
        sm.setChatId(Long.toString(chatId));
        sm.setParseMode("Markdown");
        try {
            tgbot.execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
