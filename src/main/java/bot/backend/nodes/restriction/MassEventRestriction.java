package bot.backend.nodes.restriction;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MassEventRestriction extends Restriction<MassEventRestriction.MassEventType> {

    List<MassEventType> massEventTypes;

    @Override
    public boolean validate(MassEventType massEvent) {
        return massEventTypes.contains(massEvent);
    }

    @Override
    public List<MassEventType> validValues() {
        return massEventTypes;
    }

    public enum MassEventType {
        FOOTBALL, BASKETBALL, FESTIVAL, FAIR, HOCKEY, TENNIS, UNKNOWN
    }
}
