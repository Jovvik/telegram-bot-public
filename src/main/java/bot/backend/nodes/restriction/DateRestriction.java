package bot.backend.nodes.restriction;

import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.Event;

import java.time.LocalDate;
import java.util.List;

public class DateRestriction extends Restriction<LocalDate> {

    LocalDate date;

    public DateRestriction(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean validate(LocalDate object) {
        return date.isEqual(object);
    }

    @Override
    public List<LocalDate> validValues() {
        return List.of(date);
    }

    public Class<? extends Event> getEventType() {
        return Event.class;
    }
}
