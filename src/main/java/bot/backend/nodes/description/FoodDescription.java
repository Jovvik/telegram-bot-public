package bot.backend.nodes.description;

import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.TimeRestriction;
import lombok.Getter;


public class FoodDescription extends Description {

    @Getter
    private final KitchenRestriction kitchenRestriction;

    public FoodDescription(
            TimeRestriction timeRestriction,
            KitchenRestriction kitchenRestriction
    ) {
        super(timeRestriction);
        this.kitchenRestriction = kitchenRestriction;
    }
}
