package bot.backend.nodes.events;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Event {

    @Getter
    public Location location;

    @Getter
    public Integer from;

    @Getter
    public Integer to;

    @Getter
    public Category category;

}
