package bot.external.maps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        MapService service = new MapService();
        service.setText("cafe");

        String response = service.sendRequest();

        System.out.println(new ObjectMapper().readValue(response, MapResponse.class));
    }
}
