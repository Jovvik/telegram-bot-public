package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.CultureDescription;
import bot.backend.nodes.description.FoodDescription;
import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.*;
import bot.entities.TagEntity;
import bot.services.LocationService;
import bot.services.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
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
                var actualRes = (CultureRestriction) res;
                tags.addAll(addTagsFromType(actualRes.getValue()));
                cultureTypes.addAll(actualRes.getValue());
            }
        });

        return new TablePredicate(Category.CULTURE, tags,0, 24 * 60,
                this.getStartDay(description.getTypedRestrictions(DateRestriction.class)));
    }

    @Override
    public CultureEvent generateEvent(CultureDescription description)
    {
        TablePredicate predicate = this.createPredicate(description);
        this.setTimeInterval(predicate, description);

        List<Location> matchedLocations = this.findLocations(predicate);
        return new CultureEvent(
                matchedLocations.get(0),
                Category.CULTURE,
                new Event.Time(predicate.getTimeFrom(), predicate.getTimeTo()),
                cultureTypes
        );
    }
}
