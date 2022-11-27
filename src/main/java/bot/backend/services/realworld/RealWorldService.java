package bot.backend.services.realworld;

import bot.backend.nodes.description.Description;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.FoodEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.Restriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.converters.LocationConverter;
import bot.entities.LocationEntity;
import bot.repositories.LocationRepository;
import bot.services.LocationService;
import bot.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class RealWorldService<E extends Event, D extends Description<E>> {

    @Autowired
    protected LocationRepository locationRepository;

    @Autowired
    protected TagService tagService;

    public List<Location> findLocations(TablePredicate predicate) {
        LocationConverter converter = new LocationConverter();

        List<LocationEntity> rawLocationEntities =
                locationRepository.getLocations(
                        predicate.getCategory(),
                        predicate.getTags(),
                        predicate.getTimeFrom(),
                        predicate.getTimeTo());

        List<Location> locations = rawLocationEntities.stream().map(converter::convertToLocation).collect(Collectors.toList());
        return handleRaw(locations);
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
