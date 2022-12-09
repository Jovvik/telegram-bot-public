package bot.backend.nodes.movie;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieSession {

    public Movie movie;

    public Event.Time time;

    // NOTE: This location doesn't have times and rating please understand this
    public Location location;
}
