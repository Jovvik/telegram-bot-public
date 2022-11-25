package bot.backend.nodes.description;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.backend.services.realworld.FoodRealWorldService;
import bot.backend.services.realworld.TablePredicate;
import lombok.Getter;

import java.util.List;


public class FoodDescription extends Description<FoodEvent> {

    private final FoodRealWorldService foodRealWorldService;

    public FoodDescription(
            List<TimeRestriction> timeRestrictions,
            List<Restriction<? extends Restriction.EventType>> restrictions,
            FoodRealWorldService foodRealWorldService) {
        super(timeRestrictions, restrictions);
        this.foodRealWorldService = foodRealWorldService;
    }

    @Override
    public FoodEvent generateEvent() {
        TablePredicate predicate = foodRealWorldService.createPredicate(this.getRestrictions());
        this.setTimeInterval(predicate);

        List<Location> matchedLocations = foodRealWorldService.findLocations(predicate);
        return new FoodEvent(matchedLocations.get(0), 0, 24*60, Category.FOOD);
    }
}
