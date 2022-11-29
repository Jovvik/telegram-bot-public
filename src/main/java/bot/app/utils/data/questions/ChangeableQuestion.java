package bot.app.utils.data.questions;

import bot.app.TelegramBot;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.function.BiFunction;

public abstract class ChangeableQuestion<T> extends BaseQuestion<T> implements Changeable<BaseQuestion<T>> {

    @Setter
    protected Integer messageId;
    public ChangeableQuestion(
            int id,
            String question,
            List<Answer<String>> answers,
            BiFunction<String, T, QuestionResult> resultBiFunction) {
        super(id, question, answers, resultBiFunction);
    }

    @Override
    public Message send(TelegramBot bot, Long chatId) throws TelegramApiException {
        Message m = super.send(bot, chatId);
        messageId = m.getMessageId();
        return m;
    }
}
