package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.*;
import bot.backend.nodes.results.TimeTable;

import java.util.List;

public class MassEventSchema extends TimeTableSchema {

    public List<Class<? extends Event>> eventOrder() {
        return List.of(
                CrowdEvent.class,
                FoodEvent.class
        );
    }

    public List<List<Integer>> possiblePermutations() {
        return List.of(List.of(1, 2));
    }

    // TODO
    public boolean canUse(List<QuestionResult> questionResults) {
        return filterByEventType(questionResults, CrowdEvent.class).size() != 0 &&
                filterByEventType(questionResults, FoodEvent.class).size() != 0;
    }

    @Override
    public ComposeResult compose(TimeTable timeTable, Event.Time globalTime) {
        return null;
    }
}