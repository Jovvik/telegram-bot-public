package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.utils.ClassField;

import java.util.List;

public class CountRestriction extends Restriction<Event, Integer> {

    public CountRestriction(Integer value) {
        super(null, value);
    }

    @Override
    public boolean validate(Integer candidate) {
        return candidate.equals(getValue());
    }

    @Override
    public Class<Event> getEventType() {
        return Event.class;
    }
}
