package bot.backend.nodes.restriction;

import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.SportEvent;
import bot.backend.nodes.events.utils.ClassField;

import java.util.List;

public class ActiveRestriction extends Restriction<ActiveEvent, ActiveEvent.ActivityType> {

    public ActiveRestriction(ActiveEvent.ActivityType value) {
        super(ActiveEvent.ACTIVITY_TYPE, value);
    }

    @Override
    public boolean validate(ActiveEvent.ActivityType sportType) {
        return validValues().contains(sportType);
    }

    public Class<ActiveEvent> getEventType() {
        return ActiveEvent.class;
    }

}