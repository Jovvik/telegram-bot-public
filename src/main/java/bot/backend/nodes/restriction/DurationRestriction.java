package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event.Duration;

import java.util.List;

public class DurationRestriction extends Restriction<Duration>{
    Duration duration;

    public DurationRestriction(Duration duration) {
        this.duration = duration;
    }

    @Override
    public boolean validate(Duration object) {
        return duration.from <= object.from && object.to <= duration.to;
    }

    @Override
    public List<Duration> validValues() {
        return List.of(duration);
    }
}
