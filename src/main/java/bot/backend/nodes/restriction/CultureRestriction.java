package bot.backend.nodes.restriction;

import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.CultureEvent.CultureType;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.utils.ClassField;

import java.util.List;

public class CultureRestriction extends Restriction<CultureEvent, CultureType> {


    public CultureRestriction(List<CultureType> values) {
        super(CultureEvent.TYPE, values);
    }

    public CultureRestriction(CultureType value) {
        super(CultureEvent.TYPE, value);
    }

    @Override
    public boolean validate(CultureType kitchen) {
        return validValues().contains(kitchen);
    }

    public Class<CultureEvent> getEventType() {
        return CultureEvent.class;
    }
}
