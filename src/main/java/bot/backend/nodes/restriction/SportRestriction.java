package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.SportEvent;
import bot.backend.nodes.events.utils.ClassField;
import lombok.AllArgsConstructor;

import java.util.List;

public class SportRestriction extends Restriction<SportEvent, SportEvent.SportType> {

    public SportRestriction(List<SportEvent.SportType> values) {
        super(SportEvent.SPORT_TYPE, values);
    }

    public SportRestriction(SportEvent.SportType value) {
        super(SportEvent.SPORT_TYPE, value);
    }

    @Override
    public boolean validate(SportEvent.SportType sportType) {
        return values.contains(sportType);
    }

    @Override
    public Class<SportEvent> getEventType() {
        return SportEvent.class;
    }


}