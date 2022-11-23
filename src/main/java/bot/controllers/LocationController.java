package bot.controllers;

import bot.entities.LocationEntity;
import bot.entities.TagEntity;
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

    @GetMapping("/addLocations")
    public void fillTable() {
        List<LocationEntity> locationEntities = new ArrayList<>();
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.address = "ksdjfkjdskfs";
        TagEntity tagEntity = new TagEntity();
        tagEntity.name = "fastfood";
        locationEntity.tags = List.of(tagEntity);
        locationEntities.add(locationEntity);
        locationEntities.forEach(locationService::save);
    }
}
