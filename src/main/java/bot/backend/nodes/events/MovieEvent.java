package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.movie.MovieSession;
import lombok.AllArgsConstructor;

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
    public enum GenreType {
        ADVENTURE("Приключения"),
        COMEDY("Комедия"),
        CRIME("Боевик"),
        DOCUMENTARY("Документальный"),
        DRAMA("Драма"),
        MELODRAMA("Мелодрама"),
        FANTASY("Фантастика"),
        HORROR("Хорор"),
        MUSICAL("Мюзикл"),
        FAMILY("Семейное"),
        THRILLER("Триллер");

        public static Map<String, MovieEvent.GenreType> map = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> map.put(it.realName, it));
        }

        private String realName;

    }


}
