package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.CultureDescription;
import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.restriction.*;
import bot.entities.TagEntity;
import bot.services.LocationService;
import bot.services.TagService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CultureRealWordService extends RealWorldService<CultureEvent, CultureDescription> {

    public final Set<CultureEvent.CultureType> cultureTypes = new HashSet<>();

    public CultureRealWordService(LocationService locationService, TagService tagService) {
        super(locationService, tagService);
    }

    @Override
    public TablePredicate createPredicate(CultureDescription description) {
        Set<TagEntity> tags = new HashSet<>();
        List<Restriction<?, ?>> restrictions = new ArrayList<>(description.restrictions.values());

        restrictions.forEach(res -> {
            if (res instanceof CultureRestriction) {
                tags.addAll(addTagsFromType(res));
                res.validValues().forEach(
                        type -> cultureTypes.add((CultureEvent.CultureType) type));
            }
        });

        return new TablePredicate(Category.CULTURE, tags,0, 24 * 60,
                this.getStartDay(description.getTypedRestrictions(DateRestriction.class)));
    }

    @Override
    public CultureEvent generateEvent(CultureDescription description) {
        return null;
    }
}
