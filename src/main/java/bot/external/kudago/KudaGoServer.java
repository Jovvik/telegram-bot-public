package bot.external.kudago;

import bot.backend.nodes.location.Location;
import bot.entities.LocationEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class KudaGoServer {

    private KudaGoConverter kudaGoConverter;

    public List<String> getAllCategories() throws IOException {
        final String API_URL = "https://kudago.com/public-api/v1.2/place-categories/?lang=ru";
        URL obj = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        JSONArray myResponse = new JSONArray(response.toString());
        for (int i = 0; i < myResponse.length(); ++i) {
            JSONObject current = myResponse.getJSONObject(i);
            System.out.println(current.get("slug"));
        }
        return null;
    }

    //https://kudago.com/public-api/v1.4/places/?page_size=20&fields=id,title,address,location,phone,site_url,coords,timetable,categories&location=spb&categories=theatre

    //https://kudago.com/public-api/v1.2/movies/?fields=status,slug



    public List<Location> getMovieByGenres(List<String> genres) throws IOException {
        final String API_URL = "https://kudago.com/public-api/v1.2/movies/?location=spb&fields=id,title,address,location,phone,site_url,coords,timetable,genres&page_size=10";
        URL obj = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray myResponse = jsonObject.getJSONArray("results");
        for (int i = 0; i < myResponse.length(); ++i) {
            JSONObject current = myResponse.getJSONObject(i);
            kudaGoConverter.convertToLocation(current);
        }
        return null;
    }


}
