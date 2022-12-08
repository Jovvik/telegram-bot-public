package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.Event.Time;
import bot.backend.nodes.events.utils.ClassField;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeRestriction extends Restriction<Event, Time> {

    public TimeRestriction(List<Time> values) {
        super(Event.TIME, values);
    }

    public TimeRestriction(Time value) {
        super(Event.TIME, value);
    }

    @Override
    public boolean validate(Time object) {
        return getValue().from <= object.from && object.to <= getValue().to;
    }

    @Override
    public List<Time> validValues() {
        Time time = getValue();
        int fullDiff = time.to - time.from;
        if (fullDiff <= 30) {
            return List.of(time);
        }

        return IntStream.range(0, fullDiff / 30)
                .mapToObj(i ->
                        new Time(
                                time.from + i * 30,
                                time.from + (i + 1) * 30
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public Class<Event> getEventType() {
        return Event.class;
    }
}
