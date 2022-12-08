package bot.backend.nodes.movie;

import bot.backend.nodes.events.MovieEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Movie {

    String title;

    Integer runningTime;

    List<MovieEvent.GenreType> genres;

}
