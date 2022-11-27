package bot.backend.nodes.restriction;

import bot.backend.nodes.events.CrowdEvent;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MassEventRestriction extends Restriction<CrowdEvent.CrowdEventType> {

    List<CrowdEvent.CrowdEventType> massEventTypes;

    @Override
    public boolean validate(CrowdEvent.CrowdEventType massEvent) {
        return massEventTypes.contains(massEvent);
    }

    @Override
    public List<CrowdEvent.CrowdEventType> validValues() {
        return massEventTypes;
    }


}
