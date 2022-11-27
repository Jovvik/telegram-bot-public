package bot.entities;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.location.Location;

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

    
    public Integer timeMondayOpen;
    public Integer timeMondayClose;

    public Integer timeTuesdayOpen;
    public Integer timeTuesdayClose;

    public Integer timeWednesdayOpen;
    public Integer timeWednesdayClose;

    public Integer timeThursdayOpen;
    public Integer timeThursdayClose;
    
    public Integer timeFridayOpen;
    public Integer timeFridayClose;

    public Integer timeSaturdayOpen;
    public Integer timeSaturdayClose;
    
    public Integer timeSundayOpen;
    public Integer timeSundayClose;

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

    public void setTime(List<Location.Time> times) {
        this.timeMondayOpen = times.get(0).getOpenTime();
        this.timeMondayClose = times.get(0).getCloseTime();
        this.timeTuesdayOpen = times.get(1).getOpenTime();
        this.timeTuesdayClose = times.get(1).getCloseTime();
        this.timeWednesdayOpen = times.get(2).getOpenTime();
        this.timeWednesdayClose = times.get(2).getCloseTime();
        this.timeThursdayOpen = times.get(3).getOpenTime();
        this.timeThursdayClose = times.get(3).getCloseTime();
        this.timeFridayOpen = times.get(4).getOpenTime();
        this.timeFridayClose = times.get(4).getCloseTime();
        this.timeSaturdayOpen = times.get(5).getOpenTime();
        this.timeSaturdayClose = times.get(5).getCloseTime();
        this.timeSundayOpen = times.get(6).getOpenTime();
        this.timeSundayClose = times.get(6).getCloseTime();
    }
}
