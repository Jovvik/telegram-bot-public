package bot.backend.nodes.restriction;

import bot.backend.nodes.events.CrowdEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.utils.ClassField;
import lombok.AllArgsConstructor;

import java.util.List;

public class CrowdEventTypeRestriction extends Restriction<CrowdEvent, CrowdEvent.CrowdEventType> {


    public CrowdEventTypeRestriction(List<CrowdEvent.CrowdEventType> values) {
        super(CrowdEvent.TYPE, values);
    }

    public CrowdEventTypeRestriction(CrowdEvent.CrowdEventType value) {
        super(CrowdEvent.TYPE, value);
    }

    @Override
    public boolean validate(CrowdEvent.CrowdEventType candidate) {
        return validValues().contains(candidate);
    }

    @Override
    public Class<CrowdEvent> getEventType() {
        return CrowdEvent.class;
    }
}
