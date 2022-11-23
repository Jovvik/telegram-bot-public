package bot.services;

import bot.entities.LocationEntity;
import bot.repositories.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationEntity save(LocationEntity location) {
        return locationRepository.save(location);
    }

}
