package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.MovieEvent;
import bot.backend.nodes.events.utils.ClassField;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;


public class GenreRestriction extends Restriction<MovieEvent, List<MovieEvent.GenreType>> {

    public GenreRestriction(List<MovieEvent.GenreType> value) {
        super(new ClassField<>(
                movieEvent -> movieEvent.movieSession.getMovie().getGenres(),
                (movieEvent, genreTypes) -> movieEvent.movieSession.getMovie().setGenres(genreTypes),
                "movieSession.movie.genres"
        ), value);
    }

    @Override
    public boolean validate(List<MovieEvent.GenreType> movieType) {
        return new HashSet<>(getValue()).containsAll(movieType);
    }

    @Override
    public Class<MovieEvent> getEventType() {
        return MovieEvent.class;
    }
}