package bot.external.kudago;

import bot.backend.nodes.movie.MovieSession;
import bot.converters.MovieConverter;
import bot.entities.MovieEntity;
import bot.services.GenreService;
import bot.services.TagService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainKudaGo {

    public static Set<String> genres =  Set.of(
            "drama", "comedy", "musical", "adventure", "thriller", "horror", "crime", "fantasy", "documentary"
    );

    public static final List<String> kudaGoCategories =  List.of(
            "theatre", // театры
            "park", //
            "museums", // музей
            "art-space", // art-space
            "recreation" // extreme

    );

    private static final KudaGoServer server = new KudaGoServer();

    public static void main(String[] args) throws IOException {
//        System.out.println(locations.get(0).getName());
    }

    public static List<MovieEntity> fillMovies(Set<String> names, GenreService genreService, TagService tagService)  {
        try {
            List<MovieResponse> responses = server.getAllFilms();
            List<MovieEntity> entities = new ArrayList<>();
            for (String name : names) {
                System.out.println("fill genre:" + name);
                entities.addAll(server.getMoviesByGenres(responses, Set.of(name)).stream()
                        .map(it -> MovieConverter.convertToMovieEntity(it, genreService, tagService))
                        .collect(Collectors.toList()));
            }
            return entities;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
