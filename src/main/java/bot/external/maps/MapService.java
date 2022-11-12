package bot.external.maps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MapService {
    private static final String API_KEY = "4c1b7a9c-d166-41d1-801c-04271f2f7ed4";
    private static final String API_URL = "https://search-maps.yandex.ru/v1/?type=biz&apikey=" + API_KEY;

    private String text;
    private String lang = "ru_RU";
    private double userLong = 30.313729;
    private double userLati = 59.954380;
    private double radiusLong = 0.03;
    private double radiusLati = 0.03;

    public String sendRequest() {
        return sendRequest(HttpResponse.BodyHandlers.ofString());
    }

    public MapResponse sendMapRequest() {
        return sendRequest(responseInfo ->
                HttpResponse.BodySubscribers.mapping(
                        HttpResponse.BodyHandlers.ofString().apply(responseInfo),
                        s -> {
                            try {
                                return new ObjectMapper().readValue(s, MapResponse.class);
                            } catch (JsonProcessingException e) {
                                return null;
                            }
                        }));
    }

    public <T> T sendRequest(HttpResponse.BodyHandler<T> handler) {
        StringBuilder urlConstructor = new StringBuilder(API_URL);

        urlConstructor
                .append("&text=").append(text)
                .append("&lang=").append(lang)
                .append("&ll=").append(userLong).append(",").append(userLati)
                .append("&spn=").append(radiusLong).append(",").append(radiusLati);

        try {
            URI uri = new URI(urlConstructor.toString());

            System.out.println(uri);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
            HttpResponse<T> response = client.send(request, handler);

            return response.body();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
