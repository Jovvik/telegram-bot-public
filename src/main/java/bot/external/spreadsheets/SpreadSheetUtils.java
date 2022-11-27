package bot.external.spreadsheets;


import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.SportEvent;
import bot.backend.nodes.restriction.SportRestriction;
import bot.backend.nodes.restriction.TimeRestriction;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    public TimeRestriction applyTime(Event.Time time) {
        return new TimeRestriction(time);
    }

    /**
     *    SPORT QUESTIONS
     **/

    public SportEvent.SportType parseSport(String sport) {
        return SportEvent.SportType.map.get(sport);
    }

    public SportRestriction applySport(SportEvent.SportType sportType) {
        return new SportRestriction(List.of(sportType));
    }




}
