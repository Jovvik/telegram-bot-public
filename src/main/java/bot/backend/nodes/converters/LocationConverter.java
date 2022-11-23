package bot.backend.nodes.converters;

import bot.backend.nodes.location.Location;
import bot.entities.LocationEntity;
import bot.entities.TagEntity;

import java.util.ArrayList;
import java.util.List;

public class LocationConverter {

    public List<TagEntity> stringToTags(List<String> stringTags) {
        return null; // TODO Миша
    }

    private String prettyStringTime(int time) {
        return (time < 10 ? "0" + time : "" + time);
    }

    private String intTimeToString(Integer time) {
        int hours = time / 60;
        int mins = time % 60;

        return prettyStringTime(hours) + ":" + prettyStringTime(mins);
    }

    public String timeToString(List<Location.Time> times, int index) {
        StringBuilder res = new StringBuilder();

        times.forEach(t ->
                res.append("[").append(intTimeToString(t.getOpenTime())).append(" - ")
                   .append(intTimeToString(t.getCloseTime())).append("]; "));
        res.delete(res.length() - 2, res.length());

        return res.toString();
    }

    private List<String> tagsToString(List<TagEntity> entityTags) {
        List<String> res = new ArrayList<>();
        entityTags.forEach(t -> res.add(t.name));
        return res;
    }

    private Integer parseTime(String time) {
        String[] splitted = time.split(":");
        return Integer.parseInt(splitted[0]) * 60 + Integer.parseInt(splitted[1]);
    }

    private Location.Time stringToTime(String string) {
        Location.Time time = new Location.Time(-1, -1);
        if (string.equals("[ - ]")) {
            return time;
        }

        String[] splittedTime = string.substring(1, string.length() - 1).split("-");
        time.setOpenTime(parseTime(splittedTime[0]));
        time.setCloseTime(parseTime(splittedTime[1]));

        return time;
    }

    private List<Location.Time> stringToTime(String timeMonday,
                                             String timeTuesday,
                                             String timeWednesday,
                                             String timeThursday,
                                             String timeFriday,
                                             String timeSaturday,
                                             String timeSunday) {

        List<Location.Time> times = new ArrayList<>();

        times.add(stringToTime(timeMonday));
        times.add(stringToTime(timeTuesday));
        times.add(stringToTime(timeWednesday));
        times.add(stringToTime(timeThursday));
        times.add(stringToTime(timeFriday));
        times.add(stringToTime(timeSaturday));
        times.add(stringToTime(timeSunday));


        return times;
    }

    public LocationEntity convertToEntity(Location location) {
        LocationEntity entity = new LocationEntity();

        entity.locationName = location.getName();
        entity.tags = stringToTags(location.getTags());
        entity.category = location.getCategory();
        entity.latitude = location.getLatitude();
        entity.longitude = location.getLongitude();
        entity.phoneNumber = location.getPhoneNumber();
        entity.url = location.getUrl();
        entity.address = location.getAddress();
        entity.timeMonday = timeToString(location.getTimes(), 0);
        entity.timeTuesday = timeToString(location.getTimes(), 1);
        entity.timeWednesday = timeToString(location.getTimes(), 2);
        entity.timeThursday = timeToString(location.getTimes(), 3);
        entity.timeFriday = timeToString(location.getTimes(), 4);
        entity.timeSaturday = timeToString(location.getTimes(), 5);
        entity.timeSunday = timeToString(location.getTimes(), 6);

        return entity;
    }

    public Location convertToLocation(LocationEntity entity) {
        return new Location(
                entity.locationName,
                tagsToString(entity.tags),
                entity.category,
                entity.latitude,
                entity.longitude,
                entity.phoneNumber,
                entity.url,
                entity.address,
                stringToTime(entity.timeMonday,
                        entity.timeTuesday,
                        entity.timeWednesday,
                        entity.timeThursday,
                        entity.timeFriday,
                        entity.timeSaturday,
                        entity.timeSunday)
        );
    }
}
