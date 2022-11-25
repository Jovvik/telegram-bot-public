package bot.backend.nodes.description;

import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.backend.nodes.events.Event;
import bot.backend.services.realworld.TablePredicate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public abstract class Description<E extends Event> {

    @Getter
    private List<TimeRestriction> timeRestrictions;

    @Getter
    private List<Restriction<? extends Restriction.EventType>> restrictions;

    protected void setTimeInterval(TablePredicate predicate) {
        predicate.setTimeFrom(timeRestrictions.get(0).validValues().get(0).getFrom());

        List<TimeRestriction.Interval> lastIntervals = timeRestrictions.get(timeRestrictions.size() - 1).validValues();
        predicate.setTimeTo(lastIntervals.get(lastIntervals.size() - 1).getTo());
    }

    public abstract E generateEvent();

}
