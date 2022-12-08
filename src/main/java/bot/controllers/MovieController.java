package bot.controllers;

import bot.backend.Main;
import bot.backend.nodes.categories.Category;
import bot.backend.nodes.movie.MovieSession;
import bot.entities.LocationEntity;
import bot.entities.MovieEntity;
import bot.external.kudago.MainKudaGo;
import bot.external.maps.MapMain;
import bot.services.GenreService;
import bot.services.LocationService;
import bot.services.MovieService;
import bot.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final GenreService genreService;

    @GetMapping("/addMovies")
    public void fillTable() {
        List<MovieEntity> movieEntities = MainKudaGo.fillMovies(MainKudaGo.genres, genreService);
        movieEntities.forEach(movieService::save);
        System.out.println(1);
    }

}
