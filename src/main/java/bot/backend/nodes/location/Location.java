package bot.backend.nodes.location;

import bot.backend.nodes.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class Location {

    public static class Time {

        public Time(Integer openTime, Integer closeTime) {
            this.openTime = openTime;
            this.closeTime = closeTime <= openTime ? closeTime + 24 * 60 : closeTime;
        }

        @Getter
        @Setter
        private Integer openTime;

        @Getter
        @Setter
        private Integer closeTime;

    }

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
    private List<Time> times;

    @Getter
    private Integer rating;


}