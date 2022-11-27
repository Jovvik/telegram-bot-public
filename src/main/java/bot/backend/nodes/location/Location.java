package bot.backend.nodes.location;

import bot.backend.nodes.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
public class Location {

    @AllArgsConstructor
    public static class Time {

        @Setter
        private Integer openTime;

        @Setter
        private Integer closeTime;

    }

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

    private List<Time> times;

    private Integer rating;


}