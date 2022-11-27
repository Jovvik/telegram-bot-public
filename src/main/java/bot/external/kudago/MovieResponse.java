package bot.external.kudago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.List;

public class MovieResponse {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("title")
    String title;

    @JsonProperty("running_time")
    Integer runningTime;

    @JsonProperty("genres")
    List<Genre> genres;

    public static class Genre {

        @JsonProperty("id")
        Integer id;

        @JsonProperty("name")
        String name;

        @JsonProperty("slug")
        String slug;
    }

}
