package bot.controllers;

import bot.backend.nodes.location.Location;
import bot.entities.TagEntity;
import bot.external.kudago.MainKudaGo;
import bot.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private static final List<String> foodCategories = List.of(
            "фастфуд", "японскийресторан", "азиатскийресторан", "кавказскийрестоан", "европейскаякухня", "суши", "пицца", "бургеры",
            "шашлыки", "рыбныйрестроан", "попитькоктейли", "шаверма", "французскийресторан", "итальянскаийресторан", "русскаякухня",
            "тайскаякухня", "китайскийресторан", "японскийресторан", "бар", "столовая"
    );

    private final TagService tagService;

    @GetMapping("/addTags")
    public void fillTable() {
        List<TagEntity> tags = new ArrayList<>();
        for (String category : foodCategories) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.name = category;
            tags.add(tagEntity);
        }

        for (String tag : MainKudaGo.kudaGoCategories) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.name = tag;
            tags.add(tagEntity);
        }
        tags.forEach(tagService::save);
    }

}
