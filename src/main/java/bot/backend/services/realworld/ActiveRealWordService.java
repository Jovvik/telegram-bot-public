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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActiveRealWordService extends RealWorldService<ActiveEvent, ActiveDescription> {

    private final List<ActiveEvent.ActiveType> activeTypes = new ArrayList<>();

    public ActiveRealWordService(LocationService locationService, TagService tagService) {
        super(locationService, tagService);
    }


    @Override
    public TablePredicate createPredicate(ActiveDescription description) {
        Set<TagEntity> tags = new HashSet<>();
        List<Restriction<?, ?>> restrictions = new ArrayList<>(description.restrictions.values());

        restrictions.forEach(res -> {
            if (res instanceof ActiveRestriction) {
                tags.addAll(addTagsFromType(res));
                res.validValues().forEach(
                        type -> activeTypes.add((ActiveEvent.ActiveType) type));
            }
        });

        return new TablePredicate(Category.ACTIVE, tags,0, 24 * 60,
                this.getStartDay(description.getTypedRestrictions(DateRestriction.class)));
    }

    @Override
    public ActiveEvent generateEvent(ActiveDescription description) {
        TablePredicate predicate = this.createPredicate(description);
        this.setTimeInterval(predicate, description);

        List<Location> matchedLocations = this.findLocations(predicate);

        return new ActiveEvent(
                matchedLocations.get(0),
                Category.ACTIVE,
                new Event.Time(predicate.getTimeFrom(), predicate.getTimeTo()),
                activeTypes
        );
    }

}
