package bot.backend.nodes.restriction;

import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.Event;

import java.util.List;

public class CultureRestriction extends Restriction<CultureRestriction.CultureType> {

    List<CultureRestriction.CultureType> cultureTypes;

    @Override
    public boolean validate(CultureRestriction.CultureType kitchen) {
        return cultureTypes.contains(kitchen);
    }

    @Override
    public List<CultureRestriction.CultureType> validValues() {
        return cultureTypes;
    }

    public enum CultureType {
        MOVIE, THEATRE, MUSICAL
    }

    public Class<? extends Event> getEventType() {
        return CultureEvent.class;
    }
}
