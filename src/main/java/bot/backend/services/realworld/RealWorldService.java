package bot.backend.services.realworld;

import bot.backend.nodes.description.Description;
import bot.backend.nodes.location.Location;

import java.util.List;

public abstract class RealWorldService<D extends Description> {
    LocationRepository locationRepository;

    public List<Location> findLocations(List<D> descriptions) {
        TablePredicate pred = createPredicate(descriptions);
        List<Location> rawLocations = locationRepository.getLocation(pred);
        return handleRaw(rawLocations);
    }

    abstract TablePredicate createPredicate(List<D> descriptions);

    protected List<Location> handleRaw(List<Location> locations) {
        // DEFAULT
        return locations;
    }

    public interface LocationRepository {
        List<Location> getLocation(TablePredicate predicate);
    }

    public static class TablePredicate {

    }
}
