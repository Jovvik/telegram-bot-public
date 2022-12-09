package bot.backend.nodes.restriction;

import bot.backend.nodes.events.CultureEvent;
import bot.backend.nodes.events.MovieEvent;
import bot.backend.nodes.events.utils.ClassField;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MovieSessionRestriction extends Restriction<MovieEvent, Set<MovieEvent.GenreType>> {

    public MovieSessionRestriction(Set<MovieEvent.GenreType> value) {
        super(new ClassField<>(
                movieEvent -> movieEvent.movieSession.getMovie().getGenres(),
                (movieEvent, genreTypes) -> movieEvent.movieSession.getMovie().setGenres(genreTypes),
                "movieSession.movie.genres"
        ), value);
    }

    @Override
    public boolean validate(Set<MovieEvent.GenreType> movieType) {
        return new HashSet<>(getValue()).containsAll(movieType);
    }

    @Override
    public Class<MovieEvent> getEventType() {
        return MovieEvent.class;
    }
}