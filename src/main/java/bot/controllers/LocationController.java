package bot.controllers;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.location.Location;
import bot.converters.LocationConverter;
import bot.entities.LocationEntity;
import bot.external.maps.MapMain;
import bot.services.LocationService;
import bot.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final TagService tagService;

    @GetMapping("/addLocationsFood")
    public void fillTable() {
        List<LocationEntity> locationEntities = MapMain.collectAllEntities(MapMain.foodCategories, Category.FOOD, tagService);
        locationEntities.forEach(locationService::save);
    }

    @GetMapping("/addAvrora")
    public void fillAvrora() {
        locationService.save(LocationConverter.convertToEntity(new Location(
                "кинотеатр Аврора",
                Set.of("кино"),
                Category.CULTURE,
                59.933938,
                30.339296,
                "+7 (921) 942 - 80 - 20",
                "https://avrora.spb.ru/seances",
                "Россия, Санкт-Петербург, Невский проспект 60",
                Collections.nCopies(7, new Event.Time(0, 1440)),
                1
        ), tagService));
    }

    @GetMapping("/addCustomLocation")
    public void fillCustom() {
        locationService.save(LocationConverter.convertToEntity(new Location(
                "Веревочный парк FunГрад",
                Set.of("веревочныйпарк"),
                Category.ACTIVE,
        59.86656003420463,
                30.35018768556594,
                "89657856160",
                "http://fungrad.ru/",
                "Россия, Санкт-Петербург, пр. Космонавтов, 14",
                Collections.nCopies(7, new Event.Time(10 * 60, 22 * 60)),
                1
        ), tagService));
    }

    @GetMapping("/delete")
    public void deleteByCategory() {
        locationService.deleteById(1296);
        locationService.deleteById(1298);
        locationService.deleteById(1302);
    }

    @GetMapping("/addLocationsSport")
    public void fillTableActive() {
        List<LocationEntity> locationEntities = MapMain.collectAllEntities(List.of("настольныйтеннис"), Category.ACTIVE, tagService);
        locationEntities.forEach(locationService::save);
        System.out.println("complete sports");
    }

    @GetMapping("/addLocationsCulture")
    public void fillTableCulture() {
        List<LocationEntity> locationEntities = MapMain.collectAllEntities(MapMain.cultureCategories,  Category.CULTURE, tagService);
        locationEntities.forEach(locationService::save);
        System.out.println("complete culture");
    }

    @GetMapping("/getLocations")
    public void getTable() {
        locationService.getAllLocations().forEach(
                location -> {
                    System.out.println(location.address);
                }
        );
    }
}
