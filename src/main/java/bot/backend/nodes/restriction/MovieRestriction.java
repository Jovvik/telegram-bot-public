package bot.backend.nodes.restriction;

import bot.backend.nodes.events.MovieEvent;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MovieRestriction extends Restriction<MovieEvent.MovieType> {

    List<MovieEvent.MovieType> movieTypes;

    @Override
    public boolean validate(MovieEvent.MovieType movieType) {
        return movieTypes.contains(movieType);
    }

    @Override
    public List<MovieEvent.MovieType> validValues() {
        return movieTypes;
    }


}