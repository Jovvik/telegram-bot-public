package bot.backend.nodes.restriction;

import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.Event;

import java.util.List;

public class ActiveRestriction extends Restriction<ActiveRestriction.SportType> {

    List<SportType> sportTypes;

    @Override
    public boolean validate(SportType sportType) {
        return sportTypes.contains(sportType);
    }

    @Override
    public List<ActiveRestriction.SportType> validValues() {
        return sportTypes;
    }

    public enum SportType {
        BASKETBALL, FOOTBALL, TENNIS, SWIMMING, SKIING
    }

    public Class<? extends Event> getEventType() {
        return ActiveEvent.class;
    }

}