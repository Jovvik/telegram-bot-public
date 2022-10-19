package bot.app.abilities;

import bot.app.TelegramBot;
import bot.app.service.EventBuilderService;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class CheckResultAbility extends AbilityTemplate {
    public CheckResultAbility(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Ability create() {
        return Ability.builder()
                .name("check")
                .info("check results")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(messageContext -> {
                    var eventService = bot.getEventBuilderService();
                    var userId = messageContext.user().getId();
                    System.out.printf("User[%s] check status of results%n", userId);
                    if (eventService.isEventDone(userId)) {
                        var plan = eventService.getResult(userId);
                        String planStr = "";
                        for (EventBuilderService.Event e : plan) {
                            planStr += e.getName() + " from " + e.getStart() + " to " + e.getEnd() + "\n";
                        }
                        SendMessage sm = new SendMessage();
                        sm.setChatId(Long.toString(getChatId(messageContext.update())));
                        sm.setText(planStr);
                        try {
                            bot.execute(sm);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        SendMessage sm = new SendMessage();
                        sm.setChatId(Long.toString(getChatId(messageContext.update())));
                        int userPosition = 1 + eventService.queuePosition(userId);
                        if (userPosition == 0) {
                            sm.setText("Your event is creating right know!");
                        } else {
                            sm.setText("Your request in queue, before you *" + (userPosition - 1) + "* people.");
                        }
                        sm.enableMarkdown(true);
                        try {
                            bot.execute(sm);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .build();
    }
}
