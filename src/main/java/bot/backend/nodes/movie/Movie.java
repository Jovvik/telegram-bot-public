package bot.backend.nodes.movie;

import bot.backend.nodes.events.MovieEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@Getter
@Setter
public class Movie {

    public String title;

    public Integer runningTime;

    public Set<MovieEvent.GenreType> genres;

    public String linkToPhoto;

}
