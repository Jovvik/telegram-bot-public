package bot.converters;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.location.Location;
import bot.entities.LocationEntity;
import bot.entities.TagEntity;
import bot.services.TagService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationConverter {

    public static Set<TagEntity> stringToTags(Set<String> stringTags, TagService tagService) {
        Set<TagEntity> tags = new HashSet<>();

        stringTags.forEach(str -> {
            TagEntity tag = new TagEntity();
            tag.name = str;
            tags.add(tagService.save(tag));
        });

        return tags;
    }

    private static Set<String> tagsToString(Set<TagEntity> entityTags) {
        Set<String> res = new HashSet<>();
        entityTags.forEach(t -> res.add(t.name));
        return res;
    }

    private static List<Event.Time> stringToTime(Integer timeMondayOpen, Integer timeMondayClose,
                                             Integer timeTuesdayOpen, Integer timeTuesdayClose,
                                             Integer timeWednesdayOpen, Integer timeWednesdayClose,
                                             Integer timeThursdayOpen, Integer timeThursdayClose,
                                             Integer timeFridayOpen, Integer timeFridayClose,
                                             Integer timeSaturdayOpen, Integer timeSaturdayClose,
                                             Integer timeSundayOpen, Integer timeSundayClose) {

        List<Event.Time> times = new ArrayList<>();

        times.add(new Event.Time(timeMondayOpen, timeMondayClose));
        times.add(new Event.Time(timeTuesdayOpen, timeTuesdayClose));
        times.add(new Event.Time(timeWednesdayOpen, timeWednesdayClose));
        times.add(new Event.Time(timeThursdayOpen, timeThursdayClose));
        times.add(new Event.Time(timeFridayOpen, timeFridayClose));
        times.add(new Event.Time(timeSaturdayOpen, timeSaturdayClose));
        times.add(new Event.Time(timeSundayOpen, timeSundayClose));

        return times;
    }

    public static LocationEntity convertToEntity(Location location, TagService tagService) {
        LocationEntity entity = new LocationEntity();
        List<Event.Time> times = location.getTimes();

        entity.locationName = location.getName();
        entity.tags = stringToTags(location.getTags(), tagService);
        entity.category = location.getCategory();
        entity.latitude = location.getLatitude();
        entity.longitude = location.getLongitude();
        entity.phoneNumber = location.getPhoneNumber();
        entity.url = location.getUrl();
        entity.address = location.getAddress();

        entity.setTime(times);

        entity.rating = location.getRating();

        return entity;
    }

    public static Location convertToLocation(LocationEntity entity) {
        return new Location(
                entity.locationName,
                tagsToString(entity.tags),
                entity.category,
                entity.latitude,
                entity.longitude,
                entity.phoneNumber,
                entity.url,
                entity.address,
                stringToTime(entity.timeMondayOpen, entity.timeMondayClose,
                        entity.timeTuesdayOpen, entity.timeTuesdayClose,
                        entity.timeWednesdayOpen, entity.timeWednesdayClose,
                        entity.timeThursdayOpen, entity.timeThursdayClose,
                        entity.timeFridayOpen, entity.timeFridayClose,
                        entity.timeSaturdayOpen, entity.timeSaturdayClose,
                        entity.timeSundayOpen, entity.timeSundayClose),
                entity.rating
        );
    }
}
