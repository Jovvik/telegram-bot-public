package bot.backend.nodes.description;

import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.Restriction;

import java.util.Map;


public class FoodDescription extends Description<FoodEvent> {

    public FoodDescription(Map<String, Restriction<?, ?>> restrictions) {
        super(FoodEvent.class, restrictions);
    }

    @Override
    public FoodEvent generateEvent() {
        return null;
    }
}
