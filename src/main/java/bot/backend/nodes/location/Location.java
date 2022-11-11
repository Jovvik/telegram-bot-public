package bot.backend.nodes.location;

import bot.backend.nodes.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Location {

    @Getter
    private String name;

    // For food: Italic, France
    // For culture: ballet, cinema
    //
    @Getter
    private List<String> tags;

    @Getter
    private Category category;

    @Getter
    private Integer openTime;

    @Getter
    private Integer closeTime;


}
