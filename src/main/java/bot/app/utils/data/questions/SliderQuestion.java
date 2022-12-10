package bot.app.utils.data.questions;

import bot.app.TelegramBot;
import bot.external.spreadsheets.questions.ChooseQuestionForm.AnswerCell.EdgeType;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SliderQuestion extends ChangeableQuestion<Answer<String>> {

    private final BiFunction<String, String, String> changeFunction;

    private String defaultValue;

    public SliderQuestion(
            int id,
            String question,
            String down,
            String defaultValue,
            String up,
            int nextId,
            BiFunction<String, String, String> changeFunction,
            BiFunction<String, Answer<String>, QuestionResult> resultBiFunction) {
        this(
                id,
                question,
                Stream.of(down, defaultValue, up)
                        .map(s -> new Answer<>(s, nextId, EdgeType.TransitionWithData))
                        .collect(Collectors.toList()),
                changeFunction,
                resultBiFunction
        );
    }

    private SliderQuestion(
            int id,
            String question,
            List<Answer<String>> answers,
            BiFunction<String, String, String> changeFunction,
            BiFunction<String, Answer<String>, QuestionResult> resultBiFunction) {
        super(
                id,
                question,
                answers,
                resultBiFunction
        );
        this.changeFunction = changeFunction;
        this.defaultValue = answers.get(1).getAnswer();
    }

    @Override
    public QuestionResult handlePressing(TelegramBot tgBot, Long chatId, int answerNumber) {
        if (answerNumber == 1) {
            return resultBiFunction.apply(question, answers.get(1));
        }

        Answer<String> value = answers.get(1);

        value.setAnswer(changeFunction.apply(value.getAnswer(), answers.get(answerNumber).getAnswer()));

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
        return answers.get(answerNumber).getAnswer();
    }

    @Override
    protected AnswerOrder answerOrder() {
        return AnswerOrder.ONE_ROW;
    }

    @Override
    public BaseQuestion<Answer<String>> copy() {
        return new SliderQuestion(
                id,
                question,
                copyAnswers(),
                changeFunction,
                resultBiFunction
        );
    }

    public List<Answer<String>> copyAnswers() {
        List<Answer<String>> newAnswers = new ArrayList<>();
        for (Answer<String> answer : answers) {
            newAnswers.add(new Answer<>(
                    answer.getAnswer(),
                    answer.getNextQuestionId(),
                    answer.getEdgeType()
            ));
        }
        newAnswers.get(1).setAnswer(defaultValue);
        return newAnswers;
    }
}
