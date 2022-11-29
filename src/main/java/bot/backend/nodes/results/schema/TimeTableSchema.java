package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.*;
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
                new FoodAndActiveEvent(),
                new FoodAndCultureEvent(),
                new MassEventSchema(),
                new OneFoodEvent(),
                new RomanticSchema()
        );
    }

}
