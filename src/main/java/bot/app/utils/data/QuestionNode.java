package bot.app.utils.data;

import bot.app.utils.data.questions.Answer;
import bot.backend.nodes.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Builder
public class QuestionNode<T> {

    @NonNull
    @Getter
    private final Category category;

    @NonNull
    @Getter
    private final String question;

    @Getter
    private final Answer<T> answer;

    public QuestionNode(String question, Answer<T> answer) {
        this(Category.DEFAULT, question, answer);
    }
}
