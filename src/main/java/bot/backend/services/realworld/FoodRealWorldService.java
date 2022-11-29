package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.Description;
import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.Restriction;
import bot.entities.TagEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodRealWorldService extends RealWorldService<FoodEvent, FoodDescription> {

    // TODO change KitchenType to KitchenTypeInfo (maybe change values in KitchenTypeInfo)
    private String kitchenToString(KitchenRestriction.KitchenType kitchen) {
        switch (kitchen) {
            case JAPANESE:
                return "японскийресторан";
            case ITALIAN:
                return "итальянскийресторан";
            case RUSSIAN:
                return "русскаякухня";
            case ALL:
                return "столовая";
        }

        return "столовая";
    }

    @Override
    public TablePredicate createPredicate(FoodDescription description) {
        Set<TagEntity> tags = new HashSet<>();

        List<Restriction<?>> restrictions = new ArrayList<>(description.restrictions.values());

        restrictions.forEach(res -> {
            if (res instanceof KitchenRestriction) {
                res.validValues().forEach(type -> {
                    TagEntity tag = tagService.findByName(
                            kitchenToString((KitchenRestriction.KitchenType) type)
                    ).orElse(null);
                    tags.add(tag);
                });
            }
        });

        return new TablePredicate(Category.FOOD, tags,0, 24 * 60);
    }

    @Override
    public FoodEvent generateEvent(FoodDescription description) {
        TablePredicate predicate = this.createPredicate(description);
        this.setTimeInterval(predicate, description);

        List<Location> matchedLocations = this.findLocations(predicate);

        // TODO change to new constructor

        return new FoodEvent(
                        matchedLocations.get(0),
                        Category.FOOD,
                        new Event.Time(predicate.getTimeFrom(), predicate.getTimeTo()),
                        null,
                        null,
                        null
                );
    }

}
