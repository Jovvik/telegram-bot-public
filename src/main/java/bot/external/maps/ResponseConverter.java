package bot.external.maps;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import bot.converters.LocationConverter;
import bot.entities.LocationEntity;
import bot.services.TagService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Setter
public class ResponseConverter {
    private MapResponse mapResponse;
    private String text;
    private TagService tagService;

    public ResponseConverter(MapResponse mapResponse) {
        this.mapResponse = mapResponse;
    }

    public ResponseConverter(MapResponse mapResponse, String text) {
        this.mapResponse = mapResponse;
        this.text = text;
    }

    public ResponseConverter(MapResponse mapResponse, String text, TagService tagService) {
        this.mapResponse = mapResponse;
        this.text = text;
        this.tagService = tagService;
    }

    private Integer parseTime(String time) {
        String[] splitted = time.split(":");
        return Integer.parseInt(splitted[0]) * 60 + Integer.parseInt(splitted[1]);
    }

    private Location.Time getInterval(List<MapResponse.Feature.Properties.CompanyMetaData.Hours.Availability.Interval> intervals) {
        if (intervals == null) {
            return new Location.Time(0, 24 * 60);
        }

        Integer timeFrom = parseTime(intervals.get(0).from);
        Integer timeTo = parseTime(intervals.get(intervals.size() - 1).to);

        return new Location.Time(timeFrom, timeTo);
    }

    private List<Location.Time> createDaysAvailabilities
            (List<MapResponse.Feature.Properties.CompanyMetaData.Hours.Availability> availabilities) {
        List<Location.Time> daysAvailabilities = new ArrayList<>(Collections.nCopies(7, new Location.Time(-1, -1)));

        availabilities.forEach(a -> {
            Location.Time interval = getInterval(a.intervals);

            if (a.everyDay) {
                for (int i = 0; i < 7; i++) {
                    daysAvailabilities.set(i, interval);
                }
            } else {
                if (a.monday) daysAvailabilities.set(0, interval);
                if (a.tuesday) daysAvailabilities.set(1, interval);
                if (a.wednesday) daysAvailabilities.set(2, interval);
                if (a.thursday) daysAvailabilities.set(3, interval);
                if (a.friday) daysAvailabilities.set(4, interval);
                if (a.saturday) daysAvailabilities.set(5, interval);
                if (a.sunday) daysAvailabilities.set(6, interval);
            }
        });

        return daysAvailabilities;
    }

    private StringBuilder getTimeIntervals(List<MapResponse.Feature.Properties.CompanyMetaData.Hours.Availability> availabilities,
                                           String joiner) {
        List<Location.Time> daysAvailabilities = createDaysAvailabilities(availabilities);
        StringBuilder res = new StringBuilder();
        daysAvailabilities.forEach(t -> res.append("[").append(t.getOpenTime()).append(" - ")
                                            .append(t.getCloseTime()).append("]").append(joiner));
        res.delete(res.length() - 1, res.length());
        return res;
    }

    @Override
    public String toString() {
        return toAnyFormat(": ");
    }

    public String toCsv() {
        return toAnyFormat(", ");
    }

    public String toAnyFormat(String joiner) {
        if (mapResponse == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        mapResponse.features.forEach(f -> {
            result.append("\"").append(text).append("\"").append(joiner)
                    .append("\"").append(f.properties.name).append("\"").append(joiner)
                    .append(f.geometry.getCoordinates()).append(joiner);

            if (f.properties.companyMetaData.phones != null) {
                result.append(f.properties.companyMetaData.phones.get(0).formatted);
            } else {
                result.append("\" \"");
            }
            result.append(joiner);

            if (f.properties.companyMetaData.url != null) {
                result.append("\"").append(f.properties.companyMetaData.url).append("\"");
            } else {
                result.append("\" \"");
            }
            result.append(joiner);

            StringBuilder timeIntervals = new StringBuilder();
            if (f.properties.companyMetaData.hours != null) {
                timeIntervals = getTimeIntervals(f.properties.companyMetaData.hours.availabilities, joiner);
            }

            result.append("\"").append(f.properties.companyMetaData.address).append("\"").append(joiner)
                .append(timeIntervals).append("\n");
        });

        return result.toString();
    }

    public List<LocationEntity> toLocationEntity(Category category, String text) {
        if (mapResponse == null) {
            return null;
        }


        List<LocationEntity> locationEntities = new ArrayList<>();

        List<MapResponse.Feature> features = mapResponse.features;

        for (int i = 0; i < features.size(); i++) {
            MapResponse.Feature f = features.get(i);
            LocationEntity entity = new LocationEntity();
            LocationConverter converter = new LocationConverter();

            entity.locationName = f.properties.name;
            entity.tags = converter.stringToTags(Set.of(text), tagService);
            entity.category = category;
            entity.latitude = f.geometry.getCoordinates().get(0);
            entity.longitude = f.geometry.getCoordinates().get(1);
            entity.rating = i;
            if (f.properties.companyMetaData.phones != null) {
                entity.phoneNumber = f.properties.companyMetaData.phones.get(0).formatted;
            } else {
                entity.phoneNumber = "";
            }

            entity.url = Objects.requireNonNullElse(f.properties.companyMetaData.url, "");

            entity.address = f.properties.companyMetaData.address;

            List<Location.Time> times = new ArrayList<>(Collections.nCopies(7, new Location.Time(0, 24 * 60)));
            if (f.properties.companyMetaData.hours != null) {
                times = createDaysAvailabilities(f.properties.companyMetaData.hours.availabilities);
            }

            entity.setTime(times);

            locationEntities.add(entity);
        }

        return locationEntities;
    }

}
