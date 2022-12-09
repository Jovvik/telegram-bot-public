package bot.converters;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.MovieEvent;
import bot.backend.nodes.movie.Movie;
import bot.backend.nodes.movie.MovieSession;
import bot.entities.MovieEntity;
import bot.services.GenreService;
import bot.services.TagService;

import java.util.stream.Collectors;

public class MovieConverter {

    private static final LocationConverter locationConverter = new LocationConverter();

    public static MovieSession convertToMovieSession(MovieEntity entity) {
        return new MovieSession(
             new Movie(
                    entity.title,
                    entity.runningTime,
                     entity.genres.stream().map(it -> MovieEvent.GenreType.englishMap.get(it.name)).collect(Collectors.toSet()),
                     entity.linkToPhoto
             ),
             new Event.Time(entity.startTime, entity.startTime + entity.runningTime),
                locationConverter.convertToLocation(entity.location)
        );
    }

    public static MovieEntity convertToMovieEntity(MovieSession session, GenreService service, TagService tagService) {
        MovieEntity entity = new MovieEntity();
        entity.title = session.movie.title;
        entity.runningTime = session.movie.runningTime;
        entity.location = locationConverter.convertToEntity(session.location, tagService);
        entity.location.id = 1293L;
        entity.genres = session.movie.genres.stream().map(it -> service.findByName(it.getTagName()).get()).collect(Collectors.toSet());
        entity.startTime = session.time.from;
        entity.linkToPhoto = session.movie.linkToPhoto;
        return entity;
    }

}
