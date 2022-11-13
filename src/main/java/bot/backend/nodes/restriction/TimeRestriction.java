package bot.backend.nodes.restriction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class TimeRestriction extends Restriction<TimeRestriction.Interval> {
    private Interval interval;

    @Override
    public boolean validate(Interval object) {
        return interval.from <= object.from && object.to <= interval.to;
    }

    @Override
    public List<Interval> validValues() {
        return List.of(interval);
    }

    @AllArgsConstructor
    public static class Interval {
        @Getter
        private Integer from;

        @Getter
        private Integer to;
    }
}
