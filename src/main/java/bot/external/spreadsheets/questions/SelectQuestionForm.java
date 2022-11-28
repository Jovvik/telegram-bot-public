package bot.external.spreadsheets.questions;

import bot.app.utils.data.questions.Answer;
import bot.app.utils.data.questions.QuestionResult;
import bot.app.utils.data.questions.SelectQuestion;
import bot.backend.nodes.categories.Category;
import bot.external.spreadsheets.utils.StringList;

import java.util.List;
import java.util.stream.Collectors;

public class SelectQuestionForm extends BaseQuestionForm<SelectQuestion, StringList> {

    public List<String> answers;
    public int nextId;

    public SelectQuestionForm(List<Object> row) throws NoSuchMethodException {
        super(row, StringList.class);
    }

    @Override
    public SelectQuestion getQuestion() {
        return new SelectQuestion(
                id,
                question,
                answers,
                nextId,
                (question, answers) ->
                        new QuestionResult(
                            question,
                            Category.DEFAULT,
                            answers,
                            applying.apply(
                                parseFunction.apply(
                                    new StringList(
                                        answers.stream()
                                            .map(Answer::getAnswer)
                                            .collect(Collectors.toList())
                                    )
                                )
                            )
                    )
        );
    }

    @Override
    protected void parseDefinition(List<String> definition) {
        this.nextId = Integer.parseInt(definition.get(definition.size() - 1));
        this.answers = definition.subList(0, definition.size() - 1);
    }

}
