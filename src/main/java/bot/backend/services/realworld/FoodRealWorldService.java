package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.*;
import bot.entities.TagEntity;
import bot.services.LocationService;
import bot.services.TagService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import bot.backend.nodes.events.FoodEvent.KitchenType;

import java.util.*;

@Service
public class FoodRealWorldService extends RealWorldService<FoodEvent, FoodDescription> {

    public FoodRealWorldService(LocationService locationService, TagService tagService) {
        super(locationService, tagService);
    }


    @Override
    public FoodTablePredicateContainer createPredicate(FoodDescription description) {
        Set<KitchenType> kitchenTypes = new HashSet<>();
        Set<FoodEvent.FoodPlaceType> foodPlaceTypes = new HashSet<>();

        Set<TagEntity> tags = new HashSet<>();
        List<Restriction<?, ?>> restrictions = new ArrayList<>(description.restrictions.values());

        restrictions.forEach(res -> {
            if (res instanceof KitchenRestriction) {
                var actualRes = (KitchenRestriction) res;
                tags.addAll(addTagsFromType(actualRes.getValue()));
                actualRes.validValues().forEach(
                        type -> kitchenTypes.addAll(type));
            } else if (res instanceof FoodPlaceTypeRestriction) {
                var actualRes = (FoodPlaceTypeRestriction) res;
                tags.addAll(addTagsFromType(actualRes.getValue()));
                actualRes.validValues().forEach(
                        type -> foodPlaceTypes.addAll(type));
            } else if (res instanceof FoodTypeRestriction) {
                var actualRes = (FoodTypeRestriction) res;
                tags.addAll(addTagsFromType(actualRes.getValue()));
            }
        });

        TablePredicate tablePredicate = new TablePredicate(Category.FOOD, tags, 0, 24 * 60,
                this.getStartDay(description.getTypedRestrictions(DateRestriction.class)));
        return new FoodTablePredicateContainer(
                tablePredicate,
                kitchenTypes,
                foodPlaceTypes
        );
    }

    @Override
    public FoodEvent generateEvent(FoodDescription description) {
        FoodTablePredicateContainer container = this.createPredicate(description);
        TablePredicate predicate = container.tablePredicate;
        this.setTimeInterval(predicate, description);

        List<Location> matchedLocations = this.findLocations(predicate);

        return new FoodEvent(
                        getRelevantLocation(matchedLocations),
                        Category.FOOD,
                        new Event.Time(predicate.getTimeFrom(), predicate.getTimeTo()),
                        new ArrayList<>(container.kitchenTypes),
                        null,
                        new ArrayList<>(container.foodPlaceTypes)
                );
    }

    @Getter
    @Setter
    public static class FoodTablePredicateContainer extends TablePredicateContainer {
        Set<KitchenType> kitchenTypes;
        Set<FoodEvent.FoodPlaceType> foodPlaceTypes;

        public FoodTablePredicateContainer(
                TablePredicate tablePredicate,
                Set<KitchenType> kitchenTypes,
                Set<FoodEvent.FoodPlaceType> foodPlaceTypes
        ) {
            super(tablePredicate);
            this.kitchenTypes = kitchenTypes;
            this.foodPlaceTypes = foodPlaceTypes;
        }
    }

}
