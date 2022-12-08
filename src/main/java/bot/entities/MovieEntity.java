package bot.entities;

import bot.backend.nodes.events.Event;
import bot.backend.nodes.events.MovieEvent;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class MovieEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    @Getter
    public String title;

    public Integer runningTime;

    @ManyToMany
    public Set<GenreEntity> genres;

    public Integer startTime;

    public String location;


}
