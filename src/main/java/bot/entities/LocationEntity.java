package bot.entities;

import bot.backend.nodes.categories.Category;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class LocationEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    public String locationName;

    @ManyToMany
    public Set<TagEntity> tags;
    
    public Category category;

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

    public Integer rating;


    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
