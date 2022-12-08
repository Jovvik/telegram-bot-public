package bot.backend.nodes.description;

import bot.backend.nodes.events.MovieEvent;
import bot.backend.nodes.restriction.Restriction;

import java.util.Map;

public class MovieDescription extends Description<MovieEvent> {

    public MovieDescription(Map<String, Restriction<?, ?>> restrictions) {
        super(MovieEvent.class, restrictions);
    }

    @Override
    public MovieEvent generateEvent() {
        return null;
    }
}

