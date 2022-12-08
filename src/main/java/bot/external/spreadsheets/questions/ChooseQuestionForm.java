package bot.external.spreadsheets.questions;

import bot.app.utils.data.questions.Answer;
import bot.app.utils.data.questions.ChooseQuestion;
import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.restriction.Restriction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChooseQuestionForm extends BaseQuestionForm<ChooseQuestion, String> {

    public List<AnswerCell> answers;

    public ChooseQuestionForm(List<Object> row) throws NoSuchMethodException {
        super(row, String.class);
    }

    @Override
    public ChooseQuestion getQuestion() {
        return new ChooseQuestion(
                id,
                question,
                answers.stream()
                        .map(a -> new Answer<>(a.key, a.nextId, a.edgeType))
                        .collect(Collectors.toList()),
                (question, answer) -> {
                    Restriction<?, ?> restriction = answer.getEdgeType() == AnswerCell.EdgeType.Transition
                            ? null
                            : applying.apply(parseFunction.apply(answer.getAnswer()));

                    // TODO change to parse category
                    return new QuestionResult(question, Category.DEFAULT, List.of(answer), restriction);
                }
        );
    }

    @Override
    protected void parseDefinition(List<String> definition) {
        this.answers = definition
                .stream()
                .map(AnswerCell::new)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class AnswerCell {
        public String key;
        public int nextId;

        public EdgeType edgeType;

        public AnswerCell(String s) {
            String[] parts = s.split(";");
            this.key = parts[0];
            this.nextId = Integer.parseInt(parts[1]);

            if (parts.length == 2) {
                this.edgeType = EdgeType.TransitionWithData;
            } else {
                this.edgeType = EdgeType.fromShortName.getOrDefault(parts[2], EdgeType.Transition);
            }
        }

        @AllArgsConstructor
        public enum EdgeType {
            Transition("t"),
            TransitionWithData("twd"); // или ничего

            final String shortName;

            static final Map<String, EdgeType> fromShortName = new HashMap<>();

            static {
                for (EdgeType e : values()) {
                    fromShortName.put(e.shortName, e);
                }
            }
        }
    }
}
