package bot.backend.nodes.description;

import bot.backend.nodes.events.OutDoorEvent;
import bot.backend.nodes.restriction.Restriction;

import java.util.Map;

public class OutDoorDescription extends Description<OutDoorEvent> {

    public OutDoorDescription(Map<String, Restriction<?, ?>> restrictions) {
        super(OutDoorEvent.class, restrictions);
    }

    @Override
    public OutDoorEvent generateEvent() {
        return null;
    }
}
