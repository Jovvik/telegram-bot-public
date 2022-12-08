package bot.backend.nodes.restriction;

import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.CultureEvent.CultureType;
import bot.backend.nodes.events.Event;

import java.util.List;

public class CultureRestriction extends Restriction<CultureType> {

    List<CultureType> cultureTypes;

    public CultureRestriction(List<CultureType> cultureTypes) {
        this.cultureTypes = cultureTypes;
    }

    @Override
    public boolean validate(CultureType kitchen) {
        return cultureTypes.contains(kitchen);
    }

    @Override
    public List<CultureType> validValues() {
        return cultureTypes;
    }

    public Class<? extends Event> getEventType() {
        return CultureEvent.class;
    }
}
