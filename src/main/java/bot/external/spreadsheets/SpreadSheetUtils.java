package bot.external.spreadsheets;

import bot.app.utils.data.DataBlock;
import bot.app.utils.data.questions.Answer;
import bot.backend.nodes.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.function.BiFunction;

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

}
