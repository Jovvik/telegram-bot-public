package bot.backend.nodes.movie;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Movie {

    String title;

    Integer runningTime;

    List<String> genres;


}
