package bot.controllers;

import bot.entities.TagEntity;
import bot.external.kudago.MainKudaGo;
import bot.external.maps.MapMain;
import bot.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;


    @GetMapping("/addTagsSport")
    public void fillTableSport() {
        List<TagEntity> tags = new ArrayList<>();
        for (String category : MapMain.sportCategories) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.name = category;
            tags.add(tagEntity);
        }
        tags.forEach(tagService::save);
    }

    @GetMapping("/addTagsCulture")
    public void fillTableCulture() {
        List<TagEntity> tags = new ArrayList<>();
        for (String category : MapMain.cultureCategories) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.name = category;
            tags.add(tagEntity);
        }
    }

    @GetMapping("/addTags")
    public void fillTable() {
//        List<TagEntity> tags = new ArrayList<>();
//        for (String category : MapMain.foodCategories) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.name = "аэротруба";
        tagService.save(tagEntity);
//            tags.add(tagEntity);
//        }

//        for (String tag : MainKudaGo.kudaGoCategories) {
//            TagEntity tagEntity = new TagEntity();
//            tagEntity.name = tag;
//            tags.add(tagEntity);
//        }
//        tags.forEach(tagService::save);
    }

}
