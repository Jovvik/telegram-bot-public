package bot.backend;

import bot.backend.nodes.description.FoodDescription;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        try {
            new FoodDescription(Map.of());
        } catch (Exception e) {
            String req = "https://static-maps.yandex.ru/1.x/?size=450,450&l=map&ll=30.313729,59.95438&spn=0.03,0.03&pt=30.304815,59.959974,pm2ywl1";
//            URI uri = new URI(req);
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.body());
//            Path f = Path.of("f.png");
//            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(f)) {
//                bufferedWriter.write(response.body());
//            }

            HttpGet request = new HttpGet(req);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            request.addHeader("content-type", "image/png");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try (FileOutputStream outstream = new FileOutputStream("download-image.png")) {
                        entity.writeTo(outstream);
                    }
                }
            }
        }
    }
}
