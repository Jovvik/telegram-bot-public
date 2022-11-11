package bot.backend.nodes.restriction;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import lombok.Getter;

import java.util.List;
import java.util.Locale;

public class Restriction {

    @Getter
    private Integer from;

    @Getter
    private Integer to;

//    @Getter
//    private List<String> tags;

    public boolean test(Location location) {
        return location.getOpenTime() >= to && location.getCloseTime() <= from;
    }
}
