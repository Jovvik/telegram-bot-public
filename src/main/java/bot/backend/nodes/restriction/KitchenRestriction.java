package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.events.FoodEvent.KitchenType;
import bot.backend.nodes.events.utils.ClassField;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;

public class KitchenRestriction extends Restriction<FoodEvent, List<KitchenType>> {

    public KitchenRestriction(List<KitchenType> value) {
        super(FoodEvent.KITCHEN_TYPES, value);
    }


    @Override
    public boolean validate(List<KitchenType> candidate) {
        if (getValue().contains(KitchenType.ALL)) return true;
        return new HashSet<>(candidate).containsAll(getValue());
    }

    @Override
    public Class<FoodEvent> getEventType() {
        return FoodEvent.class;
    }
}
