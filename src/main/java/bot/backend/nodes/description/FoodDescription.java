package bot.backend.nodes.description;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.backend.services.realworld.FoodRealWorldService;
import lombok.Getter;

import java.util.List;


public class FoodDescription extends Description<FoodEvent> {

    @Getter
    private final KitchenRestriction kitchenRestriction;

    private final FoodRealWorldService foodRealWorldService;

    public FoodDescription(
            List<TimeRestriction> timeRestriction,
            KitchenRestriction kitchenRestriction,
            FoodRealWorldService foodRealWorldService) {
        super(timeRestriction);
        this.kitchenRestriction = kitchenRestriction;
        this.foodRealWorldService = foodRealWorldService;
    }

    @Override
    public FoodEvent generateEvent() {
        return new FoodEvent(foodRealWorldService.findLocations(this).get(0), 0, 24*60, Category.FOOD);
    }
}
