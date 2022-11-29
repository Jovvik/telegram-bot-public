package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.SportEvent;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SportRestriction extends Restriction<SportEvent.SportType> {

    List<SportEvent.SportType> sportTypes;

    @Override
    public boolean validate(SportEvent.SportType sportType) {
        return sportTypes.contains(sportType);
    }

    @Override
    public List<SportEvent.SportType> validValues() {
        return sportTypes;
    }

    @Override
    public Class<? extends Event> getEventType() {
        return SportEvent.class;
    }


}