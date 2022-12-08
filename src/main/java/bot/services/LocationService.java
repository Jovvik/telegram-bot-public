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

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationEntity save(LocationEntity location) {
        return locationRepository.save(location);
    }

    public void deleteById(long id) {
        locationRepository.deleteById(id);
    }


    public List<LocationEntity> getAllLocations() {
        return locationRepository.findAll();
    }

    private Boolean checkTimeAvailability(Integer timeOpen, Integer timeClose, Integer timeFrom, Integer timeTo) {
        return (timeOpen <= timeFrom && timeTo <= timeClose) || (timeOpen == 0 && timeClose == 24 * 60);
    }

    public List<LocationEntity> getLocations(Category category, Set<TagEntity> tags, Integer timeFrom, Integer timeTo, DayOfWeek startDay) {
        List<LocationEntity> validLocations = locationRepository.findByCategory(category);

        validLocations = validLocations.stream()
                .filter(loc -> {
                    switch (startDay) {
                        case MONDAY: {
                            return checkTimeAvailability(loc.timeMondayOpen, loc.timeMondayClose, timeFrom, timeTo);
                        }
                        case TUESDAY: {
                            return checkTimeAvailability(loc.timeTuesdayOpen, loc.timeTuesdayClose, timeFrom, timeTo);
                        } case WEDNESDAY: {
                            return checkTimeAvailability(loc.timeWednesdayOpen, loc.timeWednesdayClose, timeFrom, timeTo);
                        } case THURSDAY: {
                            return checkTimeAvailability(loc.timeThursdayOpen, loc.timeThursdayClose, timeFrom, timeTo);
                        } case FRIDAY: {
                            return checkTimeAvailability(loc.timeFridayOpen, loc.timeFridayClose, timeFrom, timeTo);
                        } case SATURDAY: {
                            return checkTimeAvailability(loc.timeSaturdayOpen, loc.timeSaturdayClose, timeFrom, timeTo);
                        } case SUNDAY: {
                            return checkTimeAvailability(loc.timeSundayOpen, loc.timeSaturdayClose, timeFrom, timeTo);
                        }
                    }

                    return false;
                })
                .filter(loc -> loc.tags.containsAll(tags)).collect(Collectors.toList());

        return validLocations;
    }

}
