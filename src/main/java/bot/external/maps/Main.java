package bot.external.maps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    private static final List<String> foodCategories = List.of(
        "фастфуд", "японскийресторан", "азиатскийресторан", "кавказскийрестоан", "европейскаякухня", "суши", "пицца", "бургеры",
        "шашлыки", "рыбныйрестроан", "попитькоктейли", "шаверма", "французскийресторан", "итальянскаийресторан", "русскаякухня",
        "тайскаякухня", "китайскийресторан", "японскийресторан", "бар", "столовая"
    );

    private static final List<String> gg = List.of("шины");

    public static void main(String[] args) throws JsonProcessingException {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("FoodData.csv"));
            Main.foodCategories.forEach(place -> collectData(place, writer));
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    private static void collectData(String word, BufferedWriter writer) {
        MapRequest mapRequest = new MapRequest(word);
        mapRequest.setResultsSize(30);

        MapService service = new MapService(mapRequest);
        MapResponse mapResponse = service.sendMapRequest();

        ResponseConverter converter = new ResponseConverter(mapResponse, word);

        try {
            writer.write(converter.toCsv());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
