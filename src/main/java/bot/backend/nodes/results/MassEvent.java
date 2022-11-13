package bot.backend.nodes.results;

import bot.backend.nodes.events.Event;

import java.util.List;

public class MassEvent {

    public List<Event> events;

    public MassEvent(List<Event> events) {
        this.events = events;
    }
}
