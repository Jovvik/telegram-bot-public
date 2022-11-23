package bot.backend.services.realworld;

import bot.backend.nodes.description.Description;
import bot.backend.nodes.location.Location;
import lombok.Getter;

import java.util.List;

public abstract class RealWorldService<D extends Description> {
    LocationRepository locationRepository;

    public List<Location> findLocations(D description) {
        TablePredicate pred = createPredicate(description);
        List<Location> rawLocations = locationRepository.getLocation(pred);
        return handleRaw(rawLocations);
    }

    abstract TablePredicate createPredicate(D description);

    protected List<Location> handleRaw(List<Location> locations) {
        // DEFAULT
        return locations;
    }

    public interface LocationRepository {
        List<Location> getLocation(TablePredicate predicate);
    }

    public static class TablePredicate {

        @Getter
        private String request;

        public TablePredicate(String request) {
            this.request = request;
        }
    }
}
