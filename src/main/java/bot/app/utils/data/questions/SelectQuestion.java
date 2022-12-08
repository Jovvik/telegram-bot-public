package bot.app.utils.data.questions;

import bot.app.TelegramBot;
import bot.external.spreadsheets.questions.ChooseQuestionForm;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelectQuestion extends ChangeableQuestion<List<Answer<String>>> {

    private final Set<Integer> chosenAnswers = new HashSet<>();

    public SelectQuestion(
            int id,
            String question,
            List<String> answers,
            int nextId,
            BiFunction<String, List<Answer<String>>, QuestionResult> resultBiFunction) {
        super(id, question, withStop(answers, nextId), resultBiFunction);
    }

    private SelectQuestion(
            int id,
            String question,
            List<Answer<String>> answers,
            BiFunction<String, List<Answer<String>>, QuestionResult> resultBiFunction) {
        this(
            id,
            question,
            answers.subList(0, answers.size() - 1).stream()
                    .map(Answer<String>::getAnswer)
                    .collect(Collectors.toList()),
            answers.get(answers.size() - 1).getNextQuestionId(),
            resultBiFunction
        );
    }

    private static List<Answer<String>> withStop(List<String> answers, int nextId) {
        List<Answer<String>> result = answers.stream()
                .map(s -> new Answer<>(s, nextId, ChooseQuestionForm.AnswerCell.EdgeType.TransitionWithData))
                .collect(Collectors.toList());
        result.add(new Answer<>("- select -", nextId, ChooseQuestionForm.AnswerCell.EdgeType.TransitionWithData));
        return result;
    }

    @Override
    public QuestionResult handlePressing(TelegramBot tgBot, Long chatId, int answerNumber) {
        if (answerNumber == answers.size() - 1) {
            return resultBiFunction.apply(
                    question,
                    IntStream.range(0, answers.size())
                            .filter(chosenAnswers::contains)
                            .mapToObj(answers::get)
                            .collect(Collectors.toList()));
        }

        if (chosenAnswers.contains(answerNumber)) {
            chosenAnswers.remove(answerNumber);
        } else {
            chosenAnswers.add(answerNumber);
        }

        var changeMarkUp = new EditMessageReplyMarkup();
        changeMarkUp.setChatId(Long.toString(chatId));
        changeMarkUp.setMessageId(messageId);

        var iku = new InlineKeyboardMarkup();
        iku.setKeyboard(getButtons());

        changeMarkUp.setReplyMarkup(iku);
        changeMarkUp.setChatId(Long.toString(chatId));
        try {
            tgBot.execute(changeMarkUp);
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String createButtonText(int answerNumber) {
        String prefix = chosenAnswers.contains(answerNumber) ? "\u2705 " : "";
        return prefix + answers.get(answerNumber).getAnswer();
    }

    @Override
    protected AnswerOrder answerOrder() {
        return AnswerOrder.TIGHT;
    }

    @Override
    public BaseQuestion<List<Answer<String>>> copy() {
        return new SelectQuestion(
                id,
                question,
                answers,
                resultBiFunction
        );
    }
}
