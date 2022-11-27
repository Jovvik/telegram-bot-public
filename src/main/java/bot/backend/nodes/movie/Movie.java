package bot.backend.nodes.movie;

import bot.backend.nodes.events.MovieEvent;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Movie {

    String title;

    Integer runningTime;

    List<MovieEvent.GenreType> genres;

}
