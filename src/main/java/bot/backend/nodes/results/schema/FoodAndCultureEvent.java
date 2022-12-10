package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.results.TimeTable;

import java.util.List;
import java.util.stream.Collectors;

public class FoodAndCultureEvent extends TimeTableSchema {

    public List<Class<? extends Event>> eventOrder() {
        return List.of(
                CultureEvent.class,
                FoodEvent.class
        );
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

    @Override
    public ComposeResult compose(TimeTable timeTable, Event.Time globalTime) {
        int duration = globalTime.to - globalTime.from;

        if (duration <
                MIN_CULTURE_DURATION_IN_MIN +
                AVG_TRANSPORT_DURATION_IN_MIN +
                MIN_FOOD_DURATION_IN_MIN
        ) {
            return new ComposeResult();
        }

        double k = (duration * 1.0 - AVG_TRANSPORT_DURATION_IN_MIN)
                / (
                        MIN_CULTURE_DURATION_IN_MIN +
                        MIN_FOOD_DURATION_IN_MIN
        );

        setTime(
                timeTable,
                globalTime,
                permute(timeTable.events,
                        List.of(
                                MIN_FOOD_DURATION_IN_MIN,
                                MIN_CULTURE_DURATION_IN_MIN
                        )
                ),
                k
        );


        return new ComposeResult(timeTable);
    }

    public void setTime(TimeTable timeTable, Event.Time globalTime, List<Integer> ts, double k) {
        timeTable.events.get(0).time.from = globalTime.from;
        timeTable.events.get(0).time.to = globalTime.from + (int)(ts.get(0) * k);

        timeTable.events.get(1).time.from = timeTable.events.get(0).time.to + AVG_TRANSPORT_DURATION_IN_MIN;
        timeTable.events.get(1).time.to = timeTable.events.get(1).time.from + (int)(ts.get(1) * k);
    }

    private static final int MIN_FOOD_DURATION_IN_MIN = 90;

    private static final int AVG_TRANSPORT_DURATION_IN_MIN = 30;

    private static final int MIN_CULTURE_DURATION_IN_MIN = 120;
}
