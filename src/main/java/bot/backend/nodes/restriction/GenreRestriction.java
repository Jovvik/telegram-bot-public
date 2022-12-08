package bot.backend.nodes.restriction;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.MovieEvent;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GenreRestriction extends Restriction<MovieEvent.GenreType> {

    List<MovieEvent.GenreType> movieTypes;

    @Override
    public boolean validate(MovieEvent.GenreType movieType) {
        return movieTypes.contains(movieType);
    }

    @Override
    public List<MovieEvent.GenreType> validValues() {
        return movieTypes;
    }

    @Override
    public Class<? extends Event> getEventType() {
        return MovieEvent.class;
    }
}