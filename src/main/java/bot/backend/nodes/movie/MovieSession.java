package bot.backend.nodes.movie;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovieSession {

    Movie movie;

    Event.Time time;

    // TODO location
    String location;
}
