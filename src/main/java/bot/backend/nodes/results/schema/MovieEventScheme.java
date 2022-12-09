package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.events.MovieEvent;
import bot.backend.nodes.results.TimeTable;

import java.util.List;

public class MovieEventScheme extends TimeTableSchema {

    public List<Class<? extends Event>> eventOrder() {
        return List.of(
                MovieEvent.class
        );
    }

    public List<List<Integer>> possiblePermutations() {
        return List.of(List.of(1));
    }

    public boolean canUse(List<QuestionResult> questionResults) {
        return filterByEventType(questionResults, MovieEvent.class).size() != 0;
    }

    @Override
    public ComposeResult compose(TimeTable timeTable, Event.Time globalTime) {
        int duration = globalTime.to - globalTime.from;

        timeTable.events.get(0).time.to = Math.max(
                timeTable.events.get(0).time.to,
                timeTable.events.get(0).time.from + MIN_DURATION_IN_MIN
        );

        return new ComposeResult(timeTable);
    }

    private static final int MIN_DURATION_IN_MIN = 180;
}
