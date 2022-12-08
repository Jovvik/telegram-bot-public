package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.events.FoodEvent.FoodPlaceType;
import bot.backend.nodes.events.utils.ClassField;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;


public class FoodPlaceTypeRestriction extends Restriction<FoodEvent, List<FoodEvent.FoodPlaceType>> {

    public FoodPlaceTypeRestriction(List<FoodPlaceType> value) {
        super(FoodEvent.FOOD_PLACE_TYPES, value);
    }

    @Override
    public boolean validate(List<FoodPlaceType> candidate) {
        return new HashSet<>(getValue()).containsAll(candidate);
    }

    public Class<FoodEvent> getEventType() {
        return FoodEvent.class;
    }
}
