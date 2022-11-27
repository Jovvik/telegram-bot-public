package bot.backend.nodes.description;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.Restriction;

import java.util.Map;


public class FoodDescription extends Description<FoodEvent> {

    public FoodDescription(Map<String, Restriction<?>> restrictions) {
        super(FoodEvent.class, restrictions);
    }

    @Override
    public FoodEvent generateEvent() {
//        TablePredicate predicate = foodRealWorldService.createPredicate(this.getRestrictions());
//        this.setTimeInterval(predicate);
//
//        List<Location> matchedLocations = foodRealWorldService.findLocations(predicate);
//        return new FoodEvent(matchedLocations.get(0), 0, 24*60, Category.FOOD);
        return null;
    }
}
