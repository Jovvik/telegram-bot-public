package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.FoodTypeRestriction;

import java.util.List;

public class OneFoodEvent extends TimeTableSchema {

    public List<Class<? extends Event>> eventOrder() {
        return List.of(
                FoodEvent.class
        );
    }

    public List<List<Integer>> possiblePermutations() {
        return List.of(List.of(1));
    }

    public boolean canUse(List<QuestionResult> questionResults) {
        return filterByEventType(questionResults, FoodEvent.class).size() != 0;
    }
}
