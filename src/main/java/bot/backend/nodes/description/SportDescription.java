package bot.backend.nodes.description;

import bot.backend.nodes.events.SportEvent;
import bot.backend.nodes.restriction.Restriction;

import java.util.Map;

public class SportDescription extends Description<SportEvent> {
    public SportDescription(Map<String, Restriction<?>> restrictions) {
        super(SportEvent.class, restrictions);
    }

    @Override
    public SportEvent generateEvent() {
        return null;
    }
}
