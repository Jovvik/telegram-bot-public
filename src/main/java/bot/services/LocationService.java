package bot.services;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import bot.backend.services.realworld.RealWorldService;
import bot.backend.services.realworld.TablePredicate;
import bot.entities.LocationEntity;
import bot.entities.TagEntity;
import bot.repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationEntity save(LocationEntity location) {
        return locationRepository.save(location);
    }

    public List<LocationEntity> getAllLocations() {
        return locationRepository.findAll();
    }

}
