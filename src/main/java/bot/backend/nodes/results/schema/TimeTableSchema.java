package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.*;
import bot.backend.nodes.results.TimeTable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor
public abstract class TimeTableSchema {

    public abstract List<Class<? extends Event>> eventOrder();


    public abstract List<List<Integer>> possiblePermutations();

    public abstract boolean canUse(List<QuestionResult> questionResults);

    public abstract ComposeResult compose(TimeTable timeTable, Event.Time globalTime);


    public boolean isSuitable(List<Event> events) {
        if (events.size() != eventOrder().size()) return false;
        for (List<Integer> perm : possiblePermutations()) {
            List<Class<? extends Event>> order = new ArrayList<>();
            for (int i : perm) {
                order.add(eventOrder().get(i - 1));
            }
            if (IntStream.range(0, order.size()).allMatch(
                    i -> order.get(i).isInstance(events.get(i)))
            ) {
                return true;
            }
        }
        return false;
    }

    public static List<QuestionResult> filterByEventType(
            List<QuestionResult> questionResults,
            Class<? extends Event> eventClass) {
        return questionResults.stream()
                .filter(qr -> eventClass.isAssignableFrom(qr.restriction.getEventType()))
                .collect(Collectors.toList());
    }

    public static List<? extends TimeTableSchema> availableSchemas() {
        return List.of(
                new MovieEventScheme(),
                new FoodAndActiveEvent(),
                new FoodAndCultureEvent(),
                new CultureEventScheme(),
                new ActiveEventScheme(),
//                new MassEventSchema(),
                new OneFoodEvent()
//                new RomanticSchema()
        );
    }

    @Getter
    @NoArgsConstructor
    public static class ComposeResult {
        boolean isSuccess = false;
        TimeTable timeTable = null;

        public ComposeResult(TimeTable timeTable) {
            this.isSuccess = true;
            this.timeTable = timeTable;
        }

    }

    protected <T> List<T> permute(List<Event> events, List<T> values) {
        for (List<Integer> permutation : possiblePermutations()) {
            if (IntStream.range(0, permutation.size()).allMatch(
                    i -> eventOrder()
                            .get(permutation.get(i) - 1)
                            .isInstance(events.get(i))
            )) {
                return permutation.stream()
                        .map(i -> values.get(i - 1))
                        .collect(Collectors.toList());
            }
        }
        return null;
    }

}
