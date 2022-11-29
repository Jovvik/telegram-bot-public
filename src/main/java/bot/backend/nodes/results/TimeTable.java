package bot.backend.nodes.results;

import bot.backend.nodes.events.Event;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TimeTable {
    public List<Event> events;
}
