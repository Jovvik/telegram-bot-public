package bot.backend.nodes.events;

import bot.backend.nodes.events.utils.ClassField;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.utils.RequiredField;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.movie.MovieSession;
import bot.backend.nodes.restriction.utils.TypedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
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
        this.movieSession = session;
    }

    @AllArgsConstructor
    public enum GenreType implements TypedEnum {
        ADVENTURE("Приключения", "adventure"),
        COMEDY("Комедия", "comedy"),
        CRIME("Боевик", "crime"),
        DOCUMENTARY("Документальный", "documentary"),
        DRAMA("Драма", "drama"),
        MELODRAMA("Мелодрама", "melodrama"),
        FANTASY("Фантастика", "fantasy"),
        HORROR("Хорор", "horror"),
        MUSICAL("Мюзикл", "musical"),
//        FAMILY("Семейное"),
        THRILLER("Триллер", "thriller");

        public static Map<String, MovieEvent.GenreType> englishMap = new HashMap<>();

        static {
            Arrays.stream(values()).forEach(it -> englishMap.put(it.tagName, it));
        }

        private String realName;

        private String tagName;

        @Override
        public String getTagName() {
            return tagName;
        }
    }


	// generated by script at 23/06/2022 18:20:08
	public static final ClassField<MovieEvent, MovieSession> MOVIE_SESSION = new ClassField<>(MovieEvent::getMovieSession, MovieEvent::setMovieSession, "movieSession");
}
