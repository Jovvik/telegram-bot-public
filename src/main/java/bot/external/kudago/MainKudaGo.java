package bot.external.kudago;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.movie.MovieSession;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.beans.Encoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MainKudaGo {

    public static final List<String> kudaGoCategories =  List.of(
            "theatre", // театры
            "park", //
            "museums", // музей
            "art-space", // art-space
            "recreation" // extreme

    );

    private static final KudaGoServer server = new KudaGoServer();

    public static void main(String[] args) throws IOException {

//        List<Location> locations = server.getPlaceByCategory("theatre", Category.CULTURE);
//        List<Location> locations = server.getPlaceByCategory("attractions", Category.CULTURE);
//        List<Location> locations = server.getPlaceByCategory("museums", Category.CULTURE);

        List<MovieSession> movieSessions = server.getMoviesByGenres(Set.of("драма"));
//        System.out.println(locations.get(0).getName());
    }
}
