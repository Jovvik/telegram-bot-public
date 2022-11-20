package bot.external.kudago;

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

    private static final String API_URL = "https://kudago.com/public-api/v1.2/place-categories/?lang=ru";


    public List<String> getPlaces() throws IOException {

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

}
