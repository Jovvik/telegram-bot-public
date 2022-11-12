package bot.external.maps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws JsonProcessingException, UnsupportedEncodingException {
        MapService service = new MapService();
        service.setText("кинотеатр");

        String response = service.sendRequest();

//        System.out.println(response);
        MapResponse mapResponse = new ObjectMapper().readValue(response, MapResponse.class);
//
        mapResponse.features.forEach(f -> System.out.println(f.properties.name + ": " + f.geometry.coordinates));
    }
}
