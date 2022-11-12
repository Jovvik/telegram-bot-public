package bot.external.maps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

    private String toUrl(String from) {
            StringBuilder sb = new StringBuilder();

            for(char ch : from.toCharArray()) {
                if(ch < (char)128) {
                    sb.append(ch);
                } else {
                    // this is ONLY valid if the uri was decoded using ISO-8859-1
                    sb.append(String.format("%%%02X", (int)ch));
                }
//                sb.append(ch);
            }

            return sb.toString();
        }
    public String sendRequest() throws UnsupportedEncodingException {
        StringBuilder urlConstructor = new StringBuilder(API_URL);

        urlConstructor
                .append("&text=").append(URLEncoder.encode(text, "UTF-8"))
                .append("&lang=").append(toUrl(lang))
                .append("&ll=").append(userLong).append(",").append(userLati)
                .append("&spn=").append(radiusLong).append(",").append(radiusLati);

        try {
            URL url = new URL(urlConstructor.toString());

            System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return "";
    }
}
