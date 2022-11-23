package bot.backend.nodes.location;

import bot.backend.nodes.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Location {

    @AllArgsConstructor
    private static class Time {

        @Getter
        private Integer openTime;

        @Getter
        private Integer closeTime;

    }

    @Getter
    private String name;

    @Getter
    private List<String> tags;

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
    private List<Time> times;

}