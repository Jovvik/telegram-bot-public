package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;

import java.util.List;

public class FoodAndActiveEvent extends TimeTableSchema {

    public List<Class<? extends Event>> eventOrder() {
        return List.of(FoodEvent.class, ActiveEvent.class);
    }

    public List<List<Integer>> possiblePermutations() {
        return List.of(
                List.of(1, 2),
                List.of(2, 1)
        );
    }

    public boolean canUse(List<QuestionResult> questionResults) {
        return true;
    }

}
