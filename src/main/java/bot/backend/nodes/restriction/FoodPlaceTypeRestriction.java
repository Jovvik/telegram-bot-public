package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.events.FoodEvent.FoodPlaceType;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FoodPlaceTypeRestriction extends Restriction<FoodEvent.FoodPlaceType> {

    List<FoodEvent.FoodPlaceType> foodPlaceTypes;

    @Override
    public boolean validate(FoodEvent.FoodPlaceType object) {
        return foodPlaceTypes.contains(object);
    }

    @Override
    public List<FoodPlaceType> validValues() {
        return foodPlaceTypes;
    }

    public Class<? extends Event> getEventType() {
        return FoodEvent.class;
    }
}
