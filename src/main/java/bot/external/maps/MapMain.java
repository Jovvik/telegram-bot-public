package bot.external.maps;

import bot.backend.nodes.categories.Category;
import bot.controllers.LocationController;
import bot.entities.LocationEntity;
import bot.entities.TagEntity;
import bot.services.TagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import kotlin.Pair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapMain {
    public static final List<String> foodCategories = List.of(
            "фастфуд", "японскийресторан", "азиатскийресторан", "кавказскийрестоан", "европейскаякухня", "суши", "пицца", "бургеры",
            "шашлыки", "рыбныйрестроан", "попитькоктейли", "шаверма", "французскийресторан", "итальянскаийресторан", "русскаякухня",
            "тайскаякухня", "китайскийресторан", "японскийресторан", "бар", "ресторан", "кафе"
    );

    public static final List<String> sportCategories = List.of(
        "футбольноеполе", "баскетбольнаяплощадка", "каток", "теннисныйкорт", "настольныйтеннис"
    );

    public static final List<String> cultureCategories = List.of(
            "театр", "опера", "выставка", "мюзикл"
    );

    private static final List<String> gg = List.of("шины");

    public static void main(String[] args) throws JsonProcessingException {
    }

    public static List<LocationEntity> collectAllEntities(List<String> categoriesList, Category category, TagService tagService) {
        List<LocationEntity> locationEntities = new ArrayList<>();
        categoriesList.forEach(place -> locationEntities.addAll(collectData(category, place, tagService)));

        Map<Pair<Double, Double>, Set<TagEntity>> locationTags = new HashMap<>();
        Map<Pair<Double, Double>, LocationEntity> locations = new HashMap<>();

        for (LocationEntity location : locationEntities) {
            Pair<Double, Double> coordinate = new Pair<>(location.getLatitude(), location.getLongitude());
            locationTags.putIfAbsent(coordinate, new HashSet<>());
            locationTags.get(coordinate).addAll(location.getTags());

            locations.putIfAbsent(coordinate, location);
        }

        List<LocationEntity> resultedLocations = new ArrayList<>();
        locationTags.forEach((locCoord, tags) -> {
            LocationEntity currLocation = locations.get(locCoord);
            currLocation.setTags(tags);
            resultedLocations.add(currLocation);
        });

        return resultedLocations;
    }

    private static void collectDataCsv(String word, BufferedWriter writer) {
        MapRequest mapRequest = new MapRequest(word);
        mapRequest.setResultsSize(20);

        MapService service = new MapService(mapRequest);
        MapResponse mapResponse = service.sendMapRequest();

        ResponseConverter converter = new ResponseConverter(mapResponse, word);

        try {
            writer.write(converter.toCsv());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static List<LocationEntity> collectData(Category category, String word, TagService tagService) {
        MapRequest mapRequest = new MapRequest(word);
        mapRequest.setResultsSize(20);

        MapService service = new MapService(mapRequest);
        MapResponse mapResponse = service.sendMapRequest();

        ResponseConverter converter = new ResponseConverter(mapResponse, word, tagService);

        return converter.toLocationEntity(category, word);
    }
}
