package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.*;

import java.util.List;

public class RomanticSchema extends TimeTableSchema {

    public List<Class<? extends Event>> eventOrder() {
        return List.of(
                    CultureEvent.class,
                    FoodEvent.class,
                    MovieEvent.class
        );
    }

    public List<List<Integer>> possiblePermutations() {
        return List.of(List.of(1, 2, 3));
    }

    public boolean canUse(List<QuestionResult> questionResults) {

        return filterByEventType(questionResults, CultureEvent.class).size() != 0 &&
                filterByEventType(questionResults, FoodEvent.class).size() != 0 &&
                filterByEventType(questionResults, MovieEvent.class).size() != 0;
    }
}