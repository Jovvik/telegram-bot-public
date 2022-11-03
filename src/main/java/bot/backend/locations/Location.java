package bot.backend.locations;

import java.util.List;

public class Location {
    public String name;

    public Integer id;

    public List<Integer> tags;

    public Location(Integer id, String name, List<Integer> tags) {
        this.id = id;
        this.name = name;
        this.tags = tags;
    }
}
