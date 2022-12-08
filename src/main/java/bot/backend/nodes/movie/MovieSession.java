package bot.backend.nodes.movie;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovieSession {

    Movie movie;

    Event.Time time;

    // NOTE: This location doesn't have times and rating please understand this
    String location;
}
