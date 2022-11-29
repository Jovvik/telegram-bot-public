package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.Restriction;

import java.util.List;
import java.util.stream.Collectors;

public class FoodAndCultureEvent extends TimeTableSchema {

    public List<Class<? extends Event>> eventOrder() {
        return List.of(FoodEvent.class, CultureEvent.class);
    }

    public List<List<Integer>> possiblePermutations() {
        return List.of(
                List.of(1, 2)
        );
    }

    // TODO
    public boolean canUse(List<QuestionResult> questionResults) {
        var foodRestrictions = filterByEventType(questionResults, FoodEvent.class);
        var cultureRestrictions = filterByEventType(questionResults, CultureEvent.class);

        return foodRestrictions.size() != 0
                && cultureRestrictions.size() != 0;
    }
}
