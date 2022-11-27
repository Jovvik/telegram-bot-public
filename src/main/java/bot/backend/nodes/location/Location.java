package bot.backend.nodes.location;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class Location {

    @Getter
    private String name;

    @Getter
    private Set<String> tags;

    @Getter
    private Category category;

    // shirota
    @Getter
    private Double latitude;

    // dolgota
    @Getter
    private Double longitude;

    @Getter
    private String phoneNumber;

    @Getter
    private String url;

    @Getter
    private String address;

    @Getter
    private List<Event.Time> times;

    @Getter
    private Integer rating;


}