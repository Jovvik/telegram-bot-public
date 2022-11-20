package bot.backend.nodes.restriction;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class KitchenRestriction extends Restriction<KitchenRestriction.Kitchen> {

    List<Kitchen> kitchens;

    @Override
    public boolean validate(Kitchen kitchen) {
        if (kitchens.contains(Kitchen.ALL)) return true;
        return kitchens.contains(kitchen);
    }

    @Override
    public List<Kitchen> validValues() {
        return kitchens;
    }

    public enum Kitchen {
        ITALIAN, JAPANESE, RUSSIAN, ALL
    }
}
