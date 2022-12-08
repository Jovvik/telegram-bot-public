package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.events.FoodEvent.KitchenType;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KitchenRestriction extends Restriction<KitchenType> {

    List<KitchenType> kitchens;

    @Override
    public boolean validate(KitchenType kitchen) {
        if (kitchens.contains(KitchenType.ALL)) return true;
        return kitchens.contains(kitchen);
    }

    @Override
    public List<KitchenType> validValues() {
        return kitchens;
    }

    @Override
    public Class<? extends Event> getEventType() {
        return FoodEvent.class;
    }
}
