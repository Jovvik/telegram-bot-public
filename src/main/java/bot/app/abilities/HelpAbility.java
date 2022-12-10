package bot.app.abilities;

import bot.app.TelegramBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class HelpAbility extends AbilityTemplate {
    public HelpAbility(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Ability create() {
        return Ability.builder()
                .name("help")
                .info("manual")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .input(0)
                .action(messageContext -> {
                    var userId = messageContext.user().getId();
                    SendMessage sm = new SendMessage();
                    sm.setChatId(Long.toString(getChatId(messageContext.update())));
                    sm.setText(
                            "*Руководство по использованию*:\n" +
                            "Чтобы получить индивидуальный план, необходимо пройти опрос, в ходе" +
                            " которого мы выясним ваши предпочтения, чтобы начать опрос вызовите `/poll`." +
                            "Если же вы хотите остановить прохождение опроса, нажмите `/stop`. Как только" +
                            " опрос завершится - начнется процесс подбора расписания, которое по готовности " +
                            "придет личным сообщением." +
                            "\n\n" +
                            "*Список команд:*\n" +
                            "`/help` - руководство по использованию\n" +
                            "`/poll` - начать опрос для составления плана\n" +
                            "`/stop` - завершить опрос преждевременно\n" +
                            "`/check` - утончить готовность или узнать место в очереди на выполнение"
                    );
                    sm.enableMarkdown(true);
                    try {
                        bot.execute(sm);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                })
                .build();
    }
}
