package bot.external.kudago;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.beans.Encoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        KudaGoServer server = new KudaGoServer();
//        List<MovieResponse> movieResponses = server.getMoviesByGenres(Set.of("драма"));
//        System.out.println(movieResponses.get(0).title);

        List<Location> locations = server.getPlaceByCategory("theatre", Category.CULTURE);
        System.out.println(locations.get(0).getName());
    }
}
