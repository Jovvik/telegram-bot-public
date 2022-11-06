package bot.external.spreadsheets;

import bot.app.utils.data.DataBlock;
import bot.backend.nodes.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.BiFunction;

@NoArgsConstructor
public class SpreadSheetUtils {

    public interface Interpreter extends BiFunction<String, String, DataBlock<?>> {}

    @AllArgsConstructor
    public static class Time {
        @Getter
        private int hours;

        @Getter
        private int minutes;
    }

    public static Interpreter timeInterpreter = (q, a) -> {
        String[] parts = a.split(":");

        return DataBlock.<Time>builder()
                .question(q)
                .category(Category.SETTINGS)
                .answer(new Time(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]))
                )
                .build();
    };
}
