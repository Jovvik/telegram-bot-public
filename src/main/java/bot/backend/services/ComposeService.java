package bot.backend.services;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.Description;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.results.MassEvent;

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


    // Generate structure of given items
    // We already have all categories and create the final structure
    // 
    // Voll -> Rest -> Opera (template)
    // Location () <->
    // Voll -> 10 - 18
    // Rest -> 9 - 17
    // Ballet  -> 15 - 20
    // Voll 10-12
    // Rest 13 - 15
    // Ballet 16-19
    public List<Event> createStructure(List<Event> descriptions) {
        return descriptions;
    }

    // The final step
    //
    public MassEvent composeEvents(List<Event> events) {
        events.sort(Comparator.comparing(Event::getFrom));
        List<Event> resultedList = new ArrayList<>();
        for (int i = 0; i < events.size(); ++i) {
            Event currentEvent = events.get(i);
            resultedList.add(currentEvent);
            if (i != events.size() - 1) {
                addWaiting(currentEvent, events.get(i + 1));
            }
        }
        return new MassEvent(resultedList);
    }

}
