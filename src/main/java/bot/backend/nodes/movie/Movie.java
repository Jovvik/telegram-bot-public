package bot.backend.nodes.movie;

import bot.backend.nodes.events.MovieEvent;
import lombok.AllArgsConstructor;

import java.util.Set;


@AllArgsConstructor
public class Movie {

    public String title;

    public Integer runningTime;

    public Set<MovieEvent.GenreType> genres;

}
