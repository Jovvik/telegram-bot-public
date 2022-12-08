package bot.backend.nodes.movie;

import bot.backend.nodes.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieSession {

    Movie movie;

    Event.Time time;

    // TODO location
    String location;
}
