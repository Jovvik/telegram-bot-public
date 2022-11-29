package bot.entities;

import bot.backend.nodes.categories.Category;
import bot.backend.nodes.events.Event;
import bot.backend.nodes.location.Location;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

// TODO update database to remove time stuff
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

    public void setTime(List<Event.Time> times) {
        this.timeMondayOpen = times.get(0).getFrom();
        this.timeMondayClose = times.get(0).getTo();
        this.timeTuesdayOpen = times.get(1).getFrom();
        this.timeTuesdayClose = times.get(1).getTo();
        this.timeWednesdayOpen = times.get(2).getFrom();
        this.timeWednesdayClose = times.get(2).getTo();
        this.timeThursdayOpen = times.get(3).getFrom();
        this.timeThursdayClose = times.get(3).getTo();
        this.timeFridayOpen = times.get(4).getFrom();
        this.timeFridayClose = times.get(4).getTo();
        this.timeSaturdayOpen = times.get(5).getFrom();
        this.timeSaturdayClose = times.get(5).getTo();
        this.timeSundayOpen = times.get(6).getFrom();
        this.timeSundayClose = times.get(6).getTo();
    }
}
