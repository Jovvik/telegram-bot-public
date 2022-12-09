package bot.controllers;

import bot.entities.GenreEntity;
import bot.external.kudago.MainKudaGo;
import bot.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/addGenres")
    public void fillTableSport() {
        List<GenreEntity> genreEntities = new ArrayList<>();
        for (String category : MainKudaGo.genres) {
            GenreEntity genreEntity = new GenreEntity();
            genreEntity.name = category;
            genreEntities.add(genreEntity);
        }
        genreEntities.forEach(genreService::save);
    }

}
