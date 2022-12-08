package bot.app.utils.data.questions;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.restriction.Restriction;

import java.util.List;
import java.util.Random;

public class GeneratedQuestionResult extends QuestionResult{
    public GeneratedQuestionResult(Category category, Restriction<?, ?> restriction) {
        super(
                "GENERATED-QR:" + new Random().nextInt(Integer.MAX_VALUE),
                category,
                List.of(),
                restriction
        );
    }

    public GeneratedQuestionResult(Restriction<?, ?> restriction) {
        this(Category.DEFAULT, restriction);
    }
}
