package bot.backend.services.realworld;

import bot.backend.nodes.categories.Category;
import bot.entities.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Set;

@Getter
@AllArgsConstructor
public class TablePredicate {

    private Category category;

    private Set<TagEntity> tags;

    @Setter
    private Integer timeFrom;

    @Setter
    private Integer timeTo;

    private DayOfWeek startDay;
}