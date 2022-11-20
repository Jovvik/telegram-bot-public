package bot.external.kudago;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {

    public static void main(String[] args) {
        KudaGoServer server = new KudaGoServer();
        try {
            server.getPlaces();
        } catch (Exception exception) {

        }
    }
}
