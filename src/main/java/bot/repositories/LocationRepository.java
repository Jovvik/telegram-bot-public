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

//    @Query("SELECT * FROM location_entity WHERE category = ?1 AND tags CONTAINS ?2 AND ")
    @Query(value = "SELECT * FROM location_entity", nativeQuery = true)
    List<LocationEntity> getLocations(Category category, Set<TagEntity> tags, Integer timeFrom, Integer timeTo);
}
