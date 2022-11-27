package bot.external.kudago;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        KudaGoServer server = new KudaGoServer();
        server.getMovieByGenres(List.of("comedy"));
    }
}
