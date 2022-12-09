package bot.external.kudago;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.MovieEvent;
import bot.backend.nodes.location.Location;
import bot.backend.nodes.movie.Movie;
import bot.backend.nodes.movie.MovieSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;



public class KudaGoServer {

    private KudaGoConverter kudaGoConverter = new KudaGoConverter();

//    private static final Map<Category, String> mapToKudaGoPlaces = Map.of(
//            Category.
//    )

    public List<String> getAllCategories() throws IOException {
        final String API_URL = "https://kudago.com/public-api/v1.2/place-categories/?lang=ru";
        String response = getRequest(API_URL);
        JSONArray myResponse = new JSONArray(response);
        for (int i = 0; i < myResponse.length(); ++i) {
            JSONObject current = myResponse.getJSONObject(i);
            System.out.println(current.get("slug"));
        }
        return null;
    }

    //https://kudago.com/public-api/v1.4/places/?page_size=20&fields=id,title,address,location,phone,site_url,coords,timetable,categories&location=spb&categories=theatre

    //https://kudago.com/public-api/v1.2/movies/?fields=status,slug

    public String getRequest(String API_URL) throws IOException {

        URL obj = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        return response.toString();
    }


    public List<Location> getPlaceByCategory(String placeName, Category category) throws IOException {
        final String API_URL = "https://kudago.com/public-api/v1.4/places/?page_size=10&fields=id,title,address,location,phone,site_url,coords,timetable,categories&location=spb&categories=" + placeName;

        JSONObject jsonObject = new JSONObject(getRequest(API_URL));
        JSONArray myResponse = jsonObject.getJSONArray("results");
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < myResponse.length(); ++i) {
            Location location = kudaGoConverter.convertToLocation(myResponse.getJSONObject(i), category, i);
            locations.add(location);
        }
        return locations;
    }

    public List<MovieResponse> getAllFilms() throws IOException {
        final String API_URL = "https://kudago.com/public-api/v1.4/movies/?location=spb&fields=id,title,genres,running_time&page_size=100&actual_since=1655924400";
        JSONObject jsonObject = new JSONObject(getRequest(API_URL));
        JSONArray myResponse = jsonObject.getJSONArray("results");
        List<MovieResponse> responses = new ArrayList<>();
        for (int i = 0; i < myResponse.length(); ++i) {
            JSONObject current = myResponse.getJSONObject(i);
            String s = current.toString();
            try {
                MovieResponse movieResponse = new ObjectMapper().readValue(s, MovieResponse.class);
                movieResponse.title = new String(movieResponse.title.getBytes(), StandardCharsets.UTF_8);
                for (MovieResponse.Genre genre : movieResponse.genres) {
                    genre.name = new String(genre.name.getBytes(), StandardCharsets.UTF_8);
                }
                responses.add(movieResponse);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return responses;
    }


    public List<MovieSession> getMoviesByGenres(List<MovieResponse> responses, Set<String> genres) throws IOException {
        List<MovieResponse> movieResponses =  responses.stream().filter(movieResponse ->
                movieResponse.genres.stream().anyMatch(it -> genres.contains(it.slug)))
                .peek(it -> it.genres = it.genres.stream().filter(genre -> MainKudaGo.genres.contains(genre.slug)).collect(Collectors.toSet())).collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<MovieResponse>(Comparator.comparing(MovieResponse::getTitle))),
                        ArrayList::new));

        List<List<MovieSession>> sessions = movieResponses.stream().map(this::createMovieSession).collect(Collectors.toList());
        List<MovieSession> sessionList = new ArrayList<>();
        for (List<MovieSession> session : sessions) {
            sessionList.addAll(session);
        }
        return sessionList;
    }

    private List<MovieSession> createMovieSession(MovieResponse movieResponse) {
        final String API_URL = "https://kudago.com/public-api/v1.4/movies/" + movieResponse.id + "/showings/?expand=movie,place&page_size=50&actual_since=1655924400&location=spb";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getRequest(API_URL));
        } catch (Exception ignored) {

        }
        assert jsonObject != null;
        JSONArray myResponse = jsonObject.getJSONArray("results");
        List<MovieSession> responses = new ArrayList<>();
        for (int i = 0; i < myResponse.length(); ++i) {
            JSONObject current = myResponse.getJSONObject(i);
            try {
                Movie movie = new Movie(
                        movieResponse.title,
                        movieResponse.runningTime,
                        // TODO:: ВОТ ТУТ МАПИМ НАЗВАНИЕ НАДО ПОМАТИТЬ С ENUM, ОБСУДИ С ЖЕНЕЙ
                        movieResponse.genres.stream().map(it-> MovieEvent.GenreType.englishMap.get(it.slug)).collect(Collectors.toSet()),
                        null);
                JSONObject place = current.getJSONObject("place");
                MovieSession session = new MovieSession(movie,
                        new Event.Time(current.getInt("datetime"), current.getInt("datetime") + movieResponse.runningTime),
                        null);
//                        new String(place.getString("title").getBytes(), StandardCharsets.UTF_8));
                responses.add(session);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return responses;
    }


}
