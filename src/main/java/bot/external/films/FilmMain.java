package bot.external.films;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.MovieEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.movie.Movie;
import bot.backend.nodes.movie.MovieSession;
import bot.converters.MovieConverter;
import bot.entities.MovieEntity;
import bot.services.GenreService;
import bot.services.TagService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static bot.backend.nodes.events.MovieEvent.GenreType.*;

public class FilmMain {

    public static String getRequest(String API_URL) throws IOException {

        URL obj = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("X-Platform", "widget");
        con.setRequestProperty("X-Application-Token", "Q4uVHhNkaQiQ1SvI098z5KEtwHT5AQII");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        return response.toString();
    }

    public static final Map<String, MovieEvent.GenreType> moviesAvroraMap = Map.of(
            "приключения", ADVENTURE,
            "психологический хоррор", HORROR,
            "комедия", COMEDY,
            "криминал", CRIME,
            "мелодрама", MELODRAMA,
            "музыка", MUSICAL,
            "драма", DRAMA,
            "фантастика", FANTASY,
            "документальный", DOCUMENTARY
            );

    public static void main(String[] args) {
    }

    public static List<MovieEntity> getSessions(GenreService genreService, TagService tagService) {
        List<MovieSession> movieSessions = new ArrayList<>();
        for (int w = 24; w <= 30; ++w) {
            oneDayRequest(movieSessions, 6, w);
        }
        for (int w = 1; w <= 3; ++w) {
            oneDayRequest(movieSessions, 7, w);
        }
        return movieSessions.stream()
                .map(it -> MovieConverter.convertToMovieEntity(it, genreService, tagService))
                .collect(Collectors.toList());
    }

    private static void oneDayRequest(List<MovieSession> movieSessions, int month, int day) {
        final String API_URL = "https://kinokassa.kinoplan24.ru/api/v2/release/playbill/flatten?city_id=2&date=2022-" + month + "-" + day;

        JSONObject jsonObject;
        Map<Integer, Movie> movies = new HashMap<>();
        try {
            jsonObject = new JSONObject(getRequest(API_URL));
            JSONArray seances = jsonObject.getJSONArray("seances");
            JSONArray films = jsonObject.getJSONArray("releases");
            for (int i = 0; i < films.length(); ++i) {
                JSONObject film = films.getJSONObject(i);
                JSONArray array = film.getJSONArray("genres");
                Set<MovieEvent.GenreType> genres = new HashSet<>();
                for (int j = 0; j < array.length(); ++j) {
                    String title = array.getJSONObject(j).getString("title");
                    if (!moviesAvroraMap.containsKey(title)) {
                        System.out.println(array.getJSONObject(j).getString("title"));
                        continue;
                    }
                    genres.add(moviesAvroraMap.get(title));
                }
                if (genres.size() == 0) {
                    continue;
                }
                movies.put(film.getInt("id"),
                        new Movie(
                                film.getString("title"),
                                film.getInt("duration"),
                                genres,
                                film.getString("poster")
                        )
                );
            }
            Location location = new Location(
                    "кинотеатр Аврора",
                    Set.of("кино"),
                    Category.CULTURE,
                    59.933938,
                    30.339296,
                    "+7 (921) 942 - 80 - 20",
                    "https://avrora.spb.ru/seances",
                    "Россия, Санкт-Петербург, Невский проспект 60",
                    Collections.nCopies(7, new Event.Time(0, 1440)),
                    1
            );
            for (int i = 0; i < seances.length(); ++i) {
                JSONObject seance = seances.getJSONObject(i);
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Date result1 = df1.parse(seance.getString("start_date_time"));
                int filmId = seance.getInt("release_id");
                if (!movies.containsKey(filmId)) {
                    continue;
                }
                movieSessions.add(new MovieSession(
                        movies.get(filmId),
                        new Event.Time((int) (result1.getTime() / 1000), (int) (result1.getTime() / 1000) + movies.get(filmId).runningTime),
                        location)
                );
            }
            System.out.println(1);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
