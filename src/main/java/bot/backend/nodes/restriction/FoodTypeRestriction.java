package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.events.FoodEvent.FoodType;
import bot.backend.nodes.events.utils.ClassField;

import java.util.HashSet;
import java.util.List;

public class FoodTypeRestriction extends Restriction<FoodEvent, List<FoodType>> {

    public FoodTypeRestriction(List<FoodType> value) {
        super(FoodEvent.FOOD_TYPES, value);
    }

    @Override
    public boolean validate(List<FoodType> candidate) {
        return new HashSet<>(getValue()).containsAll(candidate);
    }

    @Override
    public Class<FoodEvent> getEventType() {
        return FoodEvent.class;
    }
}
