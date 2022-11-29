package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.Description;
import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.FoodPlaceTypeRestriction;
import bot.backend.nodes.restriction.FoodTypeRestriction;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.Restriction;
import bot.entities.TagEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FoodRealWorldService extends RealWorldService<FoodEvent, FoodDescription> {

    private final Set<KitchenRestriction.KitchenType> kitchenTypes = new HashSet<>();
    private final Set<FoodEvent.FoodPlaceType> foodPlaceTypes = new HashSet<>();

    @Override
    public TablePredicate createPredicate(FoodDescription description) {
        Set<TagEntity> tags = new HashSet<>();
        List<Restriction<?>> restrictions = new ArrayList<>(description.restrictions.values());

        restrictions.forEach(res -> {
            if (res instanceof KitchenRestriction) {
                tags.addAll(addTagsFromType(res));
                res.validValues().forEach(
                        type -> kitchenTypes.add((KitchenRestriction.KitchenType) type));
            } else if (res instanceof FoodPlaceTypeRestriction) {
                tags.addAll(addTagsFromType(res));
                res.validValues().forEach(
                        type -> foodPlaceTypes.add((FoodEvent.FoodPlaceType) type));
            } else if (res instanceof FoodTypeRestriction) {
                tags.addAll(addTagsFromType(res));
            }
        });

        return new TablePredicate(Category.FOOD, tags,0, 24 * 60);
    }

    @Override
    public FoodEvent generateEvent(FoodDescription description) {
        TablePredicate predicate = this.createPredicate(description);
        this.setTimeInterval(predicate, description);

        List<Location> matchedLocations = this.findLocations(predicate);

        return new FoodEvent(
                        matchedLocations.get(0),
                        Category.FOOD,
                        new Event.Time(predicate.getTimeFrom(), predicate.getTimeTo()),
                        new ArrayList<>(kitchenTypes),
                        null,
                        new ArrayList<>(foodPlaceTypes)
                );
    }

}
