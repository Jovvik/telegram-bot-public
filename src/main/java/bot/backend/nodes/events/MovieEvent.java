package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.movie.MovieSession;
import bot.backend.nodes.restriction.TypedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MovieEvent extends Event {

    @RequiredField
    public MovieSession movieSession;

    private MovieEvent(Location location, Category category, Time time) {
        super(location, category, time);
    }

    public MovieEvent(Location location,
                      Category category,
                      Time time,
                      MovieSession session
    ) {
        super(location, category, time);
        this.movieSession = movieSession;
    }

    @AllArgsConstructor
    public enum GenreType implements TypedEnum {
        ADVENTURE("Приключения", "adventure"),
        COMEDY("Комедия", "comedy"),
        CRIME("Боевик", "crime"),
        DOCUMENTARY("Документальный", "documentary"),
        DRAMA("Драма", "drama"),
//        MELODRAMA("Мелодрама", ),
        FANTASY("Фантастика", "fantasy"),
        HORROR("Хорор", "horror"),
        MUSICAL("Мюзикл", "musical"),
//        FAMILY("Семейное"),
        THRILLER("Триллер", "thriller");

        public static Map<String, MovieEvent.GenreType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.tagName, it));
        }

        private String realName;

        @Getter
        private final String tagName;

    }


}
