package bot.backend.nodes.results;

import bot.backend.nodes.events.Event;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TimeTable {
    public List<Event> events;

    public <E extends Event> List<E> getTypedEvents(Class<E> eventClass) {
        return events.stream()
                .filter(eventClass::isInstance)
                .map(eventClass::cast)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "**Расписание вашего мероприятия:**" + events;
    }
}
