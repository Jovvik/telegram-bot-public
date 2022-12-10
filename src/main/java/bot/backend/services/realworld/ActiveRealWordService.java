package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.description.ActiveDescription;
import bot.backend.nodes.events.ActiveEvent;
import bot.backend.nodes.events.Event;
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
public class ActiveRealWordService extends RealWorldService<ActiveEvent, ActiveDescription> {

    public ActiveRealWordService(LocationService locationService, TagService tagService) {
        super(locationService, tagService);
    }

    @Override
    public ActiveTablePredicateContainer createPredicate(ActiveDescription description) {
        List<ActiveEvent.ActiveType> activeTypes = new ArrayList<>();
        Set<TagEntity> tags = new HashSet<>();
        List<Restriction<?, ?>> restrictions = new ArrayList<>(description.restrictions.values());

        restrictions.forEach(res -> {
            if (res instanceof ActiveRestriction) {
                var actualRes = (ActiveRestriction) res;
                tags.addAll(addTagsFromType(actualRes.getValue()));
                activeTypes.addAll(actualRes.getValue());
            }
        });

        return new ActiveTablePredicateContainer(
                new TablePredicate(
                        Category.ACTIVE, tags,
                        0,
                        24 * 60,
                        this.getStartDay(description.getTypedRestrictions(DateRestriction.class))
                ),
                activeTypes
        );
    }

    @Override
    public ActiveEvent generateEvent(ActiveDescription description) {
        ActiveTablePredicateContainer container = this.createPredicate(description);
        TablePredicate predicate = container.getTablePredicate();
        this.setTimeInterval(predicate, description);

        List<Location> matchedLocations = this.findLocations(predicate);

        if (matchedLocations.size() == 0) {
            return null;
        }

        return new ActiveEvent(
                getRelevantLocation(matchedLocations),
                Category.ACTIVE,
                new Event.Time(predicate.getTimeFrom(), predicate.getTimeTo()),
                container.activeTypes
        );
    }

    @Getter
    @Setter
    public static class ActiveTablePredicateContainer extends TablePredicateContainer {
        List<ActiveEvent.ActiveType> activeTypes;

        public ActiveTablePredicateContainer(TablePredicate tablePredicate, List<ActiveEvent.ActiveType> activeTypes) {
            super(tablePredicate);
            this.activeTypes = activeTypes;
        }
    }

}
