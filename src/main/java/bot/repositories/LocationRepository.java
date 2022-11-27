package bot.repositories;

import bot.backend.nodes.categories.Category;
import bot.entities.LocationEntity;
import bot.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    @Query(value = "SELECT * FROM location_entity WHERE category = ?1 AND " +
            "(((time_monday_open <= ?3 AND time_monday_close >= ?4) OR (time_monday_open = 0 AND time_monday_close = 1440)) " +
            "OR ((time_tuesday_open <= ?3 AND time_tuesday_close >= ?4) OR (time_tuesday_open = 0 AND time_tuesday_close = 1440)) " +
            "OR ((time_wednesday_open <= ?3 AND time_wednesday_close >= ?4) OR (time_wednesday_open = 0 AND time_wednesday_close = 1440)) " +
            "OR ((time_thursday_open <= ?3 AND time_thursday_close >= ?4) OR (time_thursday_open = 0 AND time_thursday_close = 1440)) " +
            "OR ((time_friday_open <= ?3 AND time_friday_close >= ?4) OR (time_friday_open = 0 AND time_friday_close = 1440)) " +
            "OR ((time_saturday_open <= ?3 AND time_saturday_close >= ?4) OR (time_saturday_open = 0 AND time_saturday_close = 1440)) " +
            "OR ((time_sunday_open <= ?3 AND time_sunday_close >= ?4) OR (time_sunday_open = 0 AND time_sunday_close = 1440)))", nativeQuery = true)
    List<LocationEntity> getLocations(Category category, Set<TagEntity> tags, Integer timeFrom, Integer timeTo);
}
