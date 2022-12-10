package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.results.TimeTable;

import java.util.List;

public class ActiveEventScheme extends TimeTableSchema {

    public List<Class<? extends Event>> eventOrder() {
        return List.of(
                ActiveEvent.class
        );
    }

    public List<List<Integer>> possiblePermutations() {
        return List.of(List.of(1));
    }

    public boolean canUse(List<QuestionResult> questionResults) {
        return filterByEventType(questionResults, ActiveEvent.class).size() != 0;
    }

    @Override
    public TimeTableSchema.ComposeResult compose(TimeTable timeTable, Event.Time globalTime) {
        int duration = globalTime.to - globalTime.from;

        timeTable.events.get(0).time.to = Math.max(
                timeTable.events.get(0).time.to,
                timeTable.events.get(0).time.from + MIN_DURATION_IN_MIN
        );

        return new TimeTableSchema.ComposeResult(timeTable);
    }

    private static final int MIN_DURATION_IN_MIN = 90;
}