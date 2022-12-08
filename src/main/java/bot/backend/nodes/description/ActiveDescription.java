package bot.backend.nodes.description;

import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.restriction.Restriction;

import java.util.Map;

public class ActiveDescription extends Description<ActiveEvent> {
    public ActiveDescription(Map<String, Restriction<?, ?>> restrictions) {
        super(ActiveEvent.class, restrictions);
    }

    @Override
    public ActiveEvent generateEvent() {
        return null;
    }
}
