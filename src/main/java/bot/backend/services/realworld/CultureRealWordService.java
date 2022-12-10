package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.CultureDescription;
import bot.backend.nodes.events.CultureEvent;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CultureRealWordService extends RealWorldService<CultureEvent, CultureDescription> {

    public CultureRealWordService(LocationService locationService, TagService tagService) {
        super(locationService, tagService);
    }

    @Override
    public CultureTablePredicateContainer createPredicate(CultureDescription description) {
        Set<CultureEvent.CultureType> cultureTypes = new HashSet<>();
        Set<TagEntity> tags = new HashSet<>();
        List<Restriction<?, ?>> restrictions = new ArrayList<>(description.restrictions.values());

        restrictions.forEach(res -> {
            if (res instanceof CultureRestriction) {
                var actualRes = (CultureRestriction) res;
                tags.addAll(addTagsFromType(actualRes.getValue()));
                cultureTypes.addAll(actualRes.getValue());
            }
        });

        TablePredicate tablePredicate = new TablePredicate(Category.CULTURE, tags, 0, 24 * 60,
                this.getStartDay(description.getTypedRestrictions(DateRestriction.class)));
        return new CultureTablePredicateContainer(
                tablePredicate,
                cultureTypes
        );
    }

    @Override
    public CultureEvent generateEvent(CultureDescription description) {
        CultureTablePredicateContainer container = this.createPredicate(description);
        TablePredicate predicate = container.tablePredicate;
        this.setTimeInterval(predicate, description);

        List<Location> matchedLocations = this.findLocations(predicate);

        if (matchedLocations.size() == 0) {
            return null;
        }

        return new CultureEvent(
                getRelevantLocation(matchedLocations),
                Category.CULTURE,
                new Event.Time(predicate.getTimeFrom(), predicate.getTimeTo()),
                container.cultureTypes
        );
    }

    @Getter
    @Setter
    public static class CultureTablePredicateContainer extends TablePredicateContainer {
        Set<CultureEvent.CultureType> cultureTypes;

        public CultureTablePredicateContainer(
                TablePredicate tablePredicate,
                Set<CultureEvent.CultureType> cultureTypes
        ) {
            super(tablePredicate);
            this.cultureTypes = cultureTypes;
        }
    }
}
