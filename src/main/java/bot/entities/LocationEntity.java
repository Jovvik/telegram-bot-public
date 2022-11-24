package bot.entities;

import bot.backend.nodes.categories.Category;

import javax.persistence.*;
import java.util.List;

@Entity
public class LocationEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    public String locationName;

    @ManyToMany
    public List<TagEntity> tags;
    
    public Category category;
    
    public String name;

    // shirota
    public Double latitude;

    // dolgota
    public Double longitude;

    
    public String phoneNumber;

    
    public String url;

    
    public String address;

    
    public String timeMonday;

    
    public String timeTuesday;

    
    public String timeWednesday;

    
    public String timeThursday;

    
    public String timeFriday;

    
    public String timeSaturday;

    
    public String timeSunday;

}
