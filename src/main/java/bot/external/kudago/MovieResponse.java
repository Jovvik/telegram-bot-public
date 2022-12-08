package bot.external.kudago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

public class MovieResponse {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("title")
    @Getter
    String title;

    @JsonProperty("running_time")
    Integer runningTime;

    @JsonProperty("genres")
    Set<Genre> genres;

    public static class Genre {

        @JsonProperty("id")
        Integer id;

        @JsonProperty("name")
        String name;

        @JsonProperty("slug")
        String slug;
    }

}
