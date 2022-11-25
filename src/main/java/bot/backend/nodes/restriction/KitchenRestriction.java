package bot.backend.nodes.restriction;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KitchenRestriction extends Restriction<KitchenRestriction.KitchenType> {

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

    public enum KitchenType implements EventType {
        ITALIAN, JAPANESE, RUSSIAN, ALL
    }
}
