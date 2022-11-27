package bot.controllers;

import bot.backend.nodes.categories.Category;
import bot.entities.LocationEntity;
import bot.entities.TagEntity;
import bot.external.maps.MapMain;
import bot.services.LocationService;
import bot.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final TagService tagService;

    @GetMapping("/addLocationsFood")
    public void fillTable() {
        List<LocationEntity> locationEntities = MapMain.collectAllEntities(Category.FOOD, tagService);
        locationEntities.forEach(locationService::save);
    }

    @GetMapping("/addLocationsKudaGo")
    public void fillTableKudaGo() {
        List<LocationEntity> locationEntities = MapMain.collectAllEntities(Category.CULTURE, tagService);
        locationEntities.forEach(locationService::save);
    }

    @GetMapping("/getLocations")
    public void getTable() {
        locationService.getLocations().forEach(
                location -> {
                    System.out.println(location.address);
                }
        );
    }
}
