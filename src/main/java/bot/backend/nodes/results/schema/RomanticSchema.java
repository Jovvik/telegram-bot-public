package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.events.MovieEvent;

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

    // TODO
    public boolean canUse(List<QuestionResult> questionResults) {
        return true;
    }
}