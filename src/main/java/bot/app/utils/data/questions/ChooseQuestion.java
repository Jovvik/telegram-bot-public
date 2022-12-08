package bot.app.utils.data.questions;

import bot.app.TelegramBot;

import java.util.List;
import java.util.function.BiFunction;

public class ChooseQuestion extends BaseQuestion<Answer<String>> {
    public ChooseQuestion(
            int id,
            String question,
            List<Answer<String>> answers,
            BiFunction<String, Answer<String>, QuestionResult> resultBiFunction) {
        super(id, question, answers, resultBiFunction);
    }

    @Override
    public QuestionResult handlePressing(TelegramBot tgBot, Long chatId, int answerNumber) {
        return resultBiFunction.apply(question, answers.get(answerNumber));
    }

    @Override
    public String createButtonText(int answerNumber) {
        return answers.get(answerNumber).getAnswer();
    }

    @Override
    protected AnswerOrder answerOrder() {
        return AnswerOrder.TIGHT;
    }
}
