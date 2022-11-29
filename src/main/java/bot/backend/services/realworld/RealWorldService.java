package bot.backend.services.realworld;

import bot.backend.nodes.description.Description;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.DateRestriction;
import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.backend.nodes.restriction.TypedEnum;
import bot.converters.LocationConverter;
import bot.entities.LocationEntity;
import bot.entities.TagEntity;
import bot.repositories.LocationRepository;
import bot.services.LocationService;
import bot.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public abstract class RealWorldService<E extends Event, D extends Description<E>> {

    @Autowired
    protected LocationService locationService;

    @Autowired
    protected TagService tagService;

    public List<Location> findLocations(TablePredicate predicate) {
        LocationConverter converter = new LocationConverter();

        List<LocationEntity> rawLocationEntities =
                locationService.getLocations(
                        predicate.getCategory(),
                        predicate.getTags(),
                        predicate.getTimeFrom(),
                        predicate.getTimeTo(),
                        predicate.getStartDay());

        List<Location> locations = rawLocationEntities.stream().map(converter::convertToLocation).collect(Collectors.toList());
        return handleRaw(locations);
    }

    protected Set<TagEntity> addTagsFromType(Restriction<?> res) {
        Set<TagEntity> tags = new HashSet<>();

        res.validValues().forEach(type -> {
            TagEntity tag = tagService.findByName(((TypedEnum) type).getTagName()).orElse(null);
            tags.add(tag);
        });

        return tags;
    }

    protected DayOfWeek getStartDay(List<DateRestriction> dateRestrictions) {
        if (dateRestrictions.size() == 0) {
            return DayOfWeek.MONDAY;
        }
        return dateRestrictions.get(0).validValues().get(0).getDayOfWeek();
    }

    abstract public TablePredicate createPredicate(Description<E> description);

    abstract public E generateEvent(Description<E> description);

    protected List<Location> handleRaw(List<Location> locations) {
        // DEFAULT
        return locations;
    }

    protected void setTimeInterval(TablePredicate predicate, Description<E> description) {
        List<TimeRestriction> timeRestrictions = description.getTypedRestrictions(TimeRestriction.class);

        predicate.setTimeFrom(timeRestrictions.get(0).validValues().get(0).getFrom());

        List<Event.Time> lastIntervals = timeRestrictions.get(timeRestrictions.size() - 1).validValues();
        predicate.setTimeTo(lastIntervals.get(lastIntervals.size() - 1).getTo());
    }
}
