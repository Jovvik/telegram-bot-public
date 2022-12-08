package bot.backend.nodes.description;

import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.Restriction;

import java.util.Map;

public class CultureDescription extends Description<CultureEvent> {

    public CultureDescription(Map<String, Restriction<?, ?>> restrictions) {
        super(CultureEvent.class, restrictions);
    }

    @Override
    public CultureEvent generateEvent() {
        return null;
    }
}
