package bot.backend.nodes.location;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
public class Location {

    private String name;

    private Set<String> tags;

    private Category category;

    // shirota
    private Double latitude;

    // dolgota
    private Double longitude;

    private String phoneNumber;

    private String url;

    private String address;

    private List<Event.Time> times;

    private Integer rating;

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}