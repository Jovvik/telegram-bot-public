package bot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class TagEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    public String name;

}
