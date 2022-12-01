package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.Event.Time;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeRestriction extends Restriction<Time> {

    @Getter
    private final Time time;

    public TimeRestriction(Time time) {
        this.time = time;
    }

    @Override
    public boolean validate(Time object) {
        return time.from <= object.from && object.to <= time.to;
    }

    @Override
    public List<Time> validValues() {
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
    public Class<? extends Event> getEventType() {
        return Event.class;
    }
}
