package bot.backend.nodes.restriction;

import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.CrowdEvent;
import bot.backend.nodes.events.Event;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CrowdEventTypeRestriction extends Restriction<CrowdEvent.CrowdEventType> {

    List<CrowdEvent.CrowdEventType> massEventTypes;

    @Override
    public boolean validate(CrowdEvent.CrowdEventType massEvent) {
        return massEventTypes.contains(massEvent);
    }

    @Override
    public List<CrowdEvent.CrowdEventType> validValues() {
        return massEventTypes;
    }

    public Class<? extends Event> getEventType() {
        return CrowdEvent.class;
    }

}
