package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.*;
import bot.entities.TagEntity;
import bot.services.LocationService;
import bot.services.TagService;
import org.springframework.stereotype.Service;
import bot.backend.nodes.events.FoodEvent.KitchenType;

import java.util.*;

@Service
public class FoodRealWorldService extends RealWorldService<FoodEvent, FoodDescription> {

    private final Set<KitchenType> kitchenTypes = new HashSet<>();
    private final Set<FoodEvent.FoodPlaceType> foodPlaceTypes = new HashSet<>();

    public FoodRealWorldService(LocationService locationService, TagService tagService) {
        super(locationService, tagService);
    }


    @Override
    public TablePredicate createPredicate(FoodDescription description) {
        Set<TagEntity> tags = new HashSet<>();
        List<Restriction<?, ?>> restrictions = new ArrayList<>(description.restrictions.values());

        restrictions.forEach(res -> {
            if (res instanceof KitchenRestriction) {
                tags.addAll(addTagsFromType(res));
                res.validValues().forEach(
                        type -> kitchenTypes.add((KitchenType) type));
            } else if (res instanceof FoodPlaceTypeRestriction) {
                tags.addAll(addTagsFromType(res));
                res.validValues().forEach(
                        type -> foodPlaceTypes.add((FoodEvent.FoodPlaceType) type));
            } else if (res instanceof FoodTypeRestriction) {
                tags.addAll(addTagsFromType(res));
            }
        });

        return new TablePredicate(Category.FOOD, tags,0, 24 * 60,
                this.getStartDay(description.getTypedRestrictions(DateRestriction.class)));
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
