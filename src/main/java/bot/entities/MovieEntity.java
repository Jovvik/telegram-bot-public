package bot.entities;

import lombok.Getter;

import javax.persistence.*;
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

    @ManyToOne
    public LocationEntity location;

    public String linkToPhoto;

}
