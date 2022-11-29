package bot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class TagEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tagEntity = (TagEntity) o;
        return id.equals(tagEntity.id) && name.equals(tagEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
