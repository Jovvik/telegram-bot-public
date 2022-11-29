package bot.backend.nodes.restriction;

import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.Event;

import java.util.List;

public class CountRestriction extends Restriction<Integer> {
    Integer count;

    public CountRestriction(int val) {
        this.count = val;
    }

    @Override
    public boolean validate(Integer val) {
        return count.equals(val);
    }

    @Override
    public List<Integer> validValues() {
        return List.of(count);
    }

    public Class<? extends Event> getEventType() {
        return Event.class;
    }
}
