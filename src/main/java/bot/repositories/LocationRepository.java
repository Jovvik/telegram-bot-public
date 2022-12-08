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

    List<LocationEntity> findByCategory(Category category);

    void deleteAllByCategory(Category category);
}
