package bot.backend.services.realworld;

import bot.backend.nodes.description.Description;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.restriction.Restriction;
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
public abstract class RealWorldService<D extends Description> {

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

    abstract public TablePredicate createPredicate(List<Restriction<? extends Restriction.EventType>> restrictions);

    protected List<Location> handleRaw(List<Location> locations) {
        // DEFAULT
        return locations;
    }
}
