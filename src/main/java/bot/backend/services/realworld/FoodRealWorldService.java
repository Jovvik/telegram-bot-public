package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.Restriction;
import bot.entities.TagEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodRealWorldService extends RealWorldService<FoodDescription> {

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
    public TablePredicate createPredicate(List<Restriction<? extends Restriction.EventType>> restrictions) {
        Set<TagEntity> tags = new HashSet<>();

        restrictions.forEach(res -> {
            if (res instanceof KitchenRestriction) {
                res.validValues().forEach(type -> {
                    TagEntity tag = tagService.findByName(kitchenToString((KitchenRestriction.KitchenType) type)).orElse(null);
                    tags.add(tag);
                });
            }
        });

        return new TablePredicate(Category.FOOD, tags,0, 24 * 60);
    }

}
