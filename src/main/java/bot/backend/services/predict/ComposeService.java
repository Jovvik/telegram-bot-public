package bot.backend.services.predict;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.results.TimeTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ComposeService {


    private final Map<Category, Integer> waitingTime = Map.of(
            Category.FOOD, 100,
            Category.ACTIVE, 100,
            Category.SETTINGS, 0,
            Category.CULTURE, 10,
            Category.DEFAULT, 50
    );

    private void addWaiting(Event currentEvent, Event nextEvent) {
        waitingTime.getOrDefault(currentEvent.category, 50);
    }


    // The final step
    //
    public TimeTable composeTimeTable(TimeTable timeTable) {
//        events.sort(Comparator.comparing(Event::getTime));
        List<Event> resultedList = new ArrayList<>();
        for (int i = 0; i < timeTable.events.size(); ++i) {
            Event currentEvent = timeTable.events.get(i);
            resultedList.add(currentEvent);
            if (i != timeTable.events.size() - 1) {
                addWaiting(currentEvent, timeTable.events.get(i + 1));
            }
        }
        return new TimeTable(resultedList);
    }

}
