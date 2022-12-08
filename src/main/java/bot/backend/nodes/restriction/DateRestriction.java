package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.utils.ClassField;

import java.time.LocalDate;
import java.util.List;

public class DateRestriction extends Restriction<Event, LocalDate> {

    public DateRestriction(List<LocalDate> values) {
        super(Event.LOCAL_DATE, values);
    }

    public DateRestriction(LocalDate value) {
        super(Event.LOCAL_DATE, value);
    }

    @Override
    public boolean validate(LocalDate object) {
        return getValue().isEqual(object);
    }

    public Class<Event> getEventType() {
        return Event.class;
    }
}
