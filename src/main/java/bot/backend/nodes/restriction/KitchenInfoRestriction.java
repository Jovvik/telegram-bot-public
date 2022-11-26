package bot.backend.nodes.restriction;

import lombok.AllArgsConstructor;
import bot.backend.nodes.events.FoodEvent.KitchenInfo;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class KitchenInfoRestriction extends Restriction<KitchenInfo> {

    List<KitchenInfo> kitchens;

    @Override
    public boolean validate(KitchenInfo kitchen) {
        if (kitchens.stream().map(KitchenInfo::getKitchen)
                .collect(Collectors.toList())
                .contains(KitchenInfo.Kitchen.ALL))
            return true;
        return kitchens.contains(kitchen);
    }

    @Override
    public List<KitchenInfo> validValues() {
        return kitchens;
    }
}
