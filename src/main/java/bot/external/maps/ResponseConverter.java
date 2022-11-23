package bot.external.maps;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.converters.LocationConverter;
import bot.entities.LocationEntity;
import bot.entities.TagEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ResponseConverter {
    private MapResponse mapResponse;
    private String text;

    public ResponseConverter(MapResponse mapResponse) {
        this.mapResponse = mapResponse;
    }

    public ResponseConverter(MapResponse mapResponse, String text) {
        this.mapResponse = mapResponse;
        this.text = text;
    }

    private StringBuilder getInterval(List<MapResponse.Feature.Properties.CompanyMetaData.Hours.Availability.Interval> intervals) {
        if (intervals == null) {
            return new StringBuilder("[00:00 - 00:00]");
        }

        StringBuilder dayAvailability = new StringBuilder("[");

        intervals.forEach(i -> dayAvailability.append(i.from).append(" - ").append(i.to).append("; "));
        dayAvailability.setCharAt(dayAvailability.length() - 2, ']');
        dayAvailability.delete(dayAvailability.length() - 1, dayAvailability.length());

        return dayAvailability;
    }

    private List<StringBuilder> createDaysAvailabilities
            (List<MapResponse.Feature.Properties.CompanyMetaData.Hours.Availability> availabilities) {
        List<StringBuilder> daysAvailabilities = new ArrayList<>(Collections.nCopies(7, new StringBuilder("[ - ]")));

        availabilities.forEach(a -> {
            StringBuilder interval = getInterval(a.intervals);

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
        List<StringBuilder> daysAvailabilities = createDaysAvailabilities(availabilities);
        return daysAvailabilities.stream().reduce(new StringBuilder(), (acc, now) -> acc.append(now).append(joiner));
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
                timeIntervals.delete(timeIntervals.length() - 1, timeIntervals.length());
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

        mapResponse.features.forEach(f -> {
            LocationEntity entity = new LocationEntity();
            LocationConverter converter = new LocationConverter();

            entity.locationName = f.properties.name;
            entity.tags = converter.stringToTags(List.of(text));
            entity.category = category;
            entity.latitude = f.geometry.getCoordinates().get(0);
            entity.longitude = f.geometry.getCoordinates().get(1);

            if (f.properties.companyMetaData.phones != null) {
                entity.phoneNumber = f.properties.companyMetaData.phones.get(0).formatted;
            } else {
                entity.phoneNumber = "";
            }

            entity.url = Objects.requireNonNullElse(f.properties.companyMetaData.url, "");

            entity.address = f.properties.companyMetaData.address;

            List<StringBuilder> timeIntervals = new ArrayList<>(Collections.nCopies(7, new StringBuilder("[ - ]")));
            if (f.properties.companyMetaData.hours != null) {
                timeIntervals = createDaysAvailabilities(f.properties.companyMetaData.hours.availabilities);
            }

            entity.timeMonday = timeIntervals.get(0).toString();
            entity.timeTuesday = timeIntervals.get(1).toString();
            entity.timeWednesday = timeIntervals.get(2).toString();
            entity.timeThursday = timeIntervals.get(3).toString();
            entity.timeFriday = timeIntervals.get(4).toString();
            entity.timeSaturday = timeIntervals.get(5).toString();
            entity.timeSunday = timeIntervals.get(6).toString();

            locationEntities.add(entity);
        });

        return locationEntities;
    }

}
