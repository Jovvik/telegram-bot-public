package bot.backend.nodes.restriction;

import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.FoodEvent;

import java.util.HashSet;
import java.util.List;

public class ActiveRestriction extends Restriction<ActiveEvent, List<ActiveEvent.ActiveType>> {

    public ActiveRestriction(List<ActiveEvent.ActiveType> values) {
        super(ActiveEvent.ACTIVE_TYPES, values);
    }

    @Override
    public boolean validate(List<ActiveEvent.ActiveType> candidate) {
        return new HashSet<>(getValue()).containsAll(candidate);
    }

    public Class<ActiveEvent> getEventType() {
        return ActiveEvent.class;
    }

}