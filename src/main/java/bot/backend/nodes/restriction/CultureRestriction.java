package bot.backend.nodes.restriction;

import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.CultureEvent.CultureType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CultureRestriction extends Restriction<CultureEvent, Set<CultureType>> {


    public CultureRestriction(Set<CultureType> values) {
        super(CultureEvent.CULTURE_TYPES, values);
    }

    @Override
    public boolean validate(Set<CultureType> candidate) {
        return new HashSet<>(getValue()).containsAll(candidate);
    }

    public Class<CultureEvent> getEventType() {
        return CultureEvent.class;
    }
}
