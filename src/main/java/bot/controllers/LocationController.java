package bot.controllers;

import bot.backend.nodes.categories.Category;
import bot.entities.LocationEntity;
import bot.external.maps.MapMain;
import bot.services.LocationService;
import bot.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/delete")
    public void deleteByCategory() {
        for (int i = 388; i <= 411; ++i) {
            locationService.deleteById(i);
        }
    }

    @GetMapping("/addLocationsSport")
    public void fillTableActive() {
        List<LocationEntity> locationEntities = MapMain.collectAllEntities(MapMain.sportCategories, Category.ACTIVE, tagService);
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
