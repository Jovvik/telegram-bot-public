package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.Event.Duration;
import bot.backend.nodes.events.utils.ClassField;

import java.util.List;

public class DurationRestriction extends Restriction<Event, Duration> {

    public DurationRestriction(List<Duration> values) {
        super(null, values);
    }

    public DurationRestriction(Duration value) {
        super(null, value);
    }

    @Override
    public boolean validate(Duration object) {
        return getValue().from <= object.from && object.to <= getValue().to;
    }

    public Class<Event> getEventType() {
        return Event.class;
    }
}
