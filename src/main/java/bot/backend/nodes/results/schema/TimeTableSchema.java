package bot.backend.nodes.results.schema;

import bot.app.utils.data.questions.QuestionResult;
import bot.backend.nodes.events.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@NoArgsConstructor
public abstract class TimeTableSchema {

//    OneFoodEvent(
//            List.of(
//                    FoodEvent.class
//            ),
//            List.of(
//                    List.of(1)
//            )
//    ),
//
//    FoodAndActiveEvent(
//            List.of(
//                    FoodEvent.class,
//                    ActiveEvent.class
//            ),
//            List.of(
//                    List.of(1, 2),
//                    List.of(2, 1)
//            )
//    ),
//
//    FoodAndCultureEvent(
//            List.of(
//                    CultureEvent.class,
//                    FoodEvent.class
//            ),
//            List.of(
//                    List.of(1, 2)
//            )
//    ),
//
//    Romantic(
//            List.of(
//                    CultureEvent.class,
//                    FoodEvent.class,
//                    MovieEvent.class
//            ),
//            List.of(
//                    List.of(1, 2, 3)
//            )
//    ),
//
//    MASS_EVENT(
//            List.of(
//                CrowdEvent.class,
//                FoodEvent.class
//            ),
//            List.of(
//                    List.of(1, 2)
//            )
//    );



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

}
