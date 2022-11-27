package bot.external.spreadsheets;


import bot.backend.nodes.events.Event.*;
import bot.backend.nodes.events.SportEvent;
import bot.backend.nodes.restriction.BudgetRestriction;
import bot.backend.nodes.restriction.DateRestriction;
import bot.backend.nodes.restriction.SportRestriction;
import bot.backend.nodes.restriction.TimeRestriction;
import lombok.experimental.UtilityClass;
import org.springframework.format.datetime.DateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@UtilityClass
public class SpreadSheetUtils {

    /**
     * TIME QUESTIONS
     **/
    private static final String TIME_FORMAT = "hh:mm";

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

    public Time parseTimeFrom(String time) {
        int timeFrom = parseTime(time, TIME_FORMAT);
        return new Time(timeFrom, Integer.MAX_VALUE);
    }

    public Time parseTimeTo(String time) {
        int timeTo = parseTime(time, TIME_FORMAT);
        return new Time(0, timeTo);
    }

    public Time parseTime(String time) {
        String[] arguments = time.split("-");
        int timeFrom = parseTime(arguments[0], TIME_FORMAT);
        int timeTo = parseTime(arguments[1], TIME_FORMAT);
        return new Time(timeFrom, timeTo);
    }

    public TimeRestriction applyTime(Time time) {
        return new TimeRestriction(time);
    }

    /**
     * DATE QUESTIONS
     */
    public LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public DateRestriction applyDate(LocalDate date) {
        return new DateRestriction(date);
    }

    /**
     * BUDGET QUESTIONS
     */
    public Budget parseBudget(String budget) {
        var borders = budget.substring(0, budget.length() - 1).split("-");
        try {
            int from = Integer.parseInt(borders[0]);
            int to = Integer.parseInt(borders[1]);
            return new Budget(from, to);
        } catch (NumberFormatException e) {
            return new Budget(0, Integer.MAX_VALUE);
        }
    }

    public BudgetRestriction applyBudget(Budget budget) {
        return new BudgetRestriction(budget);
    }

    /**
     * SPORT QUESTIONS
     **/
    public SportEvent.SportType parseSport(String sport) {
        return SportEvent.SportType.map.get(sport);
    }

    public SportRestriction applySport(SportEvent.SportType sportType) {
        return new SportRestriction(List.of(sportType));
    }


}
