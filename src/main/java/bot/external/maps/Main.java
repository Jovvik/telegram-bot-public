package bot.external.maps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        MapService service = new MapService();
        service.setText("кинотеатр");

        MapResponse mapResponse = service.sendMapRequest();
        mapResponse.features.forEach(f -> System.out.println(f.properties.name + ": " + f.geometry.coordinates));
    }
}
