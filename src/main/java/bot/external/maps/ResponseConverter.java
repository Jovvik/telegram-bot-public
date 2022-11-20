package bot.external.maps;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private StringBuilder getTimeIntervals(List<MapResponse.Feature.Properties.CompanyMetaData.Hours.Availability> availabilities,
                                           String joiner) {
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

}
