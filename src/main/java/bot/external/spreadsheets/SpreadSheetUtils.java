package bot.external.spreadsheets;


import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.SportEvent;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.SportRestriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.external.spreadsheets.utils.StringList;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class SpreadSheetUtils {

    /**
     *    TIME QUESTIONS
     **/

    private static final String DATE_FORMAT = "hh:mm";

    private int parseTime(String time, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        int result = 0;
        try {
            Date date = format.parse(time);
            result = date.getHours() * 60 + date.getMinutes();
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public Event.Time parseTimeFrom(String time) {
        int timeFrom = parseTime(time, DATE_FORMAT);
        return new Event.Time(timeFrom, Integer.MAX_VALUE);
    }

    public Event.Time parseTimeTo(String time) {
        int timeTo = parseTime(time, DATE_FORMAT);
        return new Event.Time(0, timeTo);
    }

    public Event.Time parseTime(String time) {
        String[] arguments = time.split("-");
        int timeFrom = parseTime(arguments[0], DATE_FORMAT);
        int timeTo = parseTime(arguments[1], DATE_FORMAT);
        return new Event.Time(timeFrom, timeTo);
    }

    public String plusMinus30(String value, String change) {
        try {
            String[] parts = value.split(":");
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int time = (h * 60 + m + (change.equals("+30m") ? 30 : -30) + 60 * 24) % (60 * 24);
            h = time / 60;
            m = time % 60;
            return (h < 10
                    ? "0" + h
                    : Integer.toString(h))
                    + ":"
                    + ((m == 0) ? "00" : "30");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public TimeRestriction applyTime(Object time) {
        return new TimeRestriction((Event.Time) time);
    }

    /**
     *    SPORT QUESTIONS
     **/

    public SportEvent.SportType parseSport(String sport) {
        return SportEvent.SportType.map.get(sport);
    }

    public SportRestriction applySport(Object sportType) {
        return new SportRestriction(List.of((SportEvent.SportType) sportType));
    }

    /**
     *      CHOOSE TEST
     */

    public Boolean parseChoose(String value) {
        return value.equals("Да");
    }

    public TimeRestriction applyChoose(Object value) {
        return new TimeRestriction((Boolean) value
                ? new Event.Time(0, 10)
                : new Event.Time(10, 0));
    }

    public List<Integer> parseIntegers(StringList values) {
        return values.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public KitchenRestriction applyIntegers(Object values) {
        List<Integer> ints = (List<Integer>) values;
        return new KitchenRestriction(ints.stream()
                .map(i -> KitchenRestriction.KitchenType.values()[i - 1])
                .collect(Collectors.toList())
        );
    }

}
