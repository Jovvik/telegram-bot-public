package bot.converters;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.MovieEvent;
import bot.backend.nodes.movie.Movie;
import bot.backend.nodes.movie.MovieSession;
import bot.entities.MovieEntity;
import bot.services.GenreService;

import java.util.stream.Collectors;

public class MovieConverter {

    public static MovieSession convertToMovieSession(MovieEntity entity) {
        return new MovieSession(
             new Movie(
                    entity.title,
                    entity.runningTime,
                     entity.genres.stream().map(it -> MovieEvent.GenreType.englishMap.get(it.name)).collect(Collectors.toSet())
             ),
             new Event.Time(entity.startTime, entity.startTime + entity.runningTime),
             entity.location
        );
    }

    public static MovieEntity convertToMovieEntity(MovieSession session, GenreService service) {
        MovieEntity entity = new MovieEntity();
        entity.title = session.movie.title;
        entity.runningTime = session.movie.runningTime;
        entity.location = session.location;
        entity.genres = session.movie.genres.stream().map(it -> service.findByName(it.getTagName()).get()).collect(Collectors.toSet());
        entity.startTime = session.time.from;
        return entity;
    }

}
