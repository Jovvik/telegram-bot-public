package bot.entities;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
public class LocationEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    public String locationName;

    @OneToMany
    public List<TagEntity> tags;

    @Getter
    public Category category;


    @Getter
    private String name;

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
    public String timeMonday;

    @Getter
    public String timeTuesday;

    @Getter
    public String timeWednesday;

    @Getter
    public String timeThursday;

    @Getter
    public String timeFriday;

    @Getter
    public String timeSaturday;

    @Getter
    public String timeSunday;


}
