package bot.external.spreadsheets;


import bot.backend.nodes.events.*;
import bot.backend.nodes.events.Event.*;
import bot.backend.nodes.events.CultureEvent.CultureType;
import bot.backend.nodes.events.FoodEvent.KitchenType;
import bot.backend.nodes.restriction.*;
import bot.backend.nodes.restriction.MovieSessionRestriction;
import bot.backend.nodes.restriction.KitchenRestriction;
import bot.backend.nodes.restriction.TimeRestriction;
import bot.external.spreadsheets.utils.StringList;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public String plusMinusDay(String value, String change) {
        try {
            var format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = parseDate(value);
            if (change.equals("-1d")) {
                if (LocalDate.now().isEqual(date)) {
                    return LocalDate.now().format(format);
                }
                return date.minusDays(1).format(format);
            } else {
                if (LocalDate.now().plusDays(29).isBefore(date)) {
                    return date.format(format);
                }
                return date.plusDays(1).format(format);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public TimeRestriction applyTime(Object time) {
        return new TimeRestriction((Event.Time) time);
    }


    /**
     * DATE QUESTIONS
     */
    public LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public DateRestriction applyDate(Object date) {
        return new DateRestriction((LocalDate) date);
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

    public BudgetRestriction applyBudget(Object budget) {
        return new BudgetRestriction((Budget) budget);
    }

    /**
     * SPORT QUESTIONS
     **/
    public ActiveEvent.ActiveType parseSport(String sport) {
        return ActiveEvent.ActiveType.map.get(sport);
    }

    public ActiveRestriction applySport(Object sportType) {
        return new ActiveRestriction(List.of((ActiveEvent.ActiveType) sportType));
    }

    /**
     * CHOOSE TEST
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
                .map(i -> KitchenType.values()[i - 1])
                .collect(Collectors.toList())
        );
    }

    /**
     * MOVIE QUESTIONS
     **/

    public MovieEvent.GenreType parseGenre(String genre) {
        return MovieEvent.GenreType.russianMap.get(genre);
    }

    public MovieSessionRestriction applyGenre(Object movieType) {
        return new MovieSessionRestriction(Set.of((MovieEvent.GenreType) movieType));
    }


    /**
     * COUNT PEOPLE
     */
    public Integer parseCount(String count) {
        return Integer.parseInt(count);
    }

    public CountRestriction applyCount(Object count) {
        return new CountRestriction((Integer) count);
    }

    /**
     * FOOD PLACE TYPE
     */
    public FoodEvent.FoodPlaceType parseFoodPlace(String place) {
        return FoodEvent.FoodPlaceType.map.get(place);
    }

    public FoodPlaceTypeRestriction applyFoodPlace(Object placeType) {
        return new FoodPlaceTypeRestriction(List.of((FoodEvent.FoodPlaceType) placeType));
    }

    /**
     * KITCHEN TYPE
     */
    public KitchenType parseKitchen(String kitchen) {
        return KitchenType.map.get(kitchen);
    }

    public KitchenRestriction applyKitchen(Object placeType) {
        return new KitchenRestriction(List.of((KitchenType) placeType));
    }

    /**
     * DURATION INFO
     */
    public Duration parseDuration(String duration) {
        var vals = duration.split(" ");
        if (vals[1].equals("часа")) {
            return new Duration(60, 120);
        }
        var borders = vals[0].split("-");
        if (borders.length == 1) {
            return new Duration(120, Integer.MAX_VALUE);
        }
        int from = Integer.parseInt(borders[0]);
        int to = Integer.parseInt(borders[1]);
        return new Duration(from, to);
    }

    public DurationRestriction applyDuration(Object duration) {
        return new DurationRestriction((Duration) duration);
    }

    /**
     * FOOD TYPE
     */
    public FoodEvent.FoodType parseFoodType(String foodType) {
        return FoodEvent.FoodType.map.get(foodType);
    }

    public FoodTypeRestriction applyFoodType(Object foodType) {
        return new FoodTypeRestriction(List.of((FoodEvent.FoodType) foodType));
    }

    /**
     * Culture type
     */
    public CultureType parseCulture(String culture) {
        return CultureType.map.get(culture);
    }

    public CultureRestriction applyCulture(Object cultureType) {
        return new CultureRestriction(Set.of((CultureType) cultureType));
    }
}
