package bot.repositories;


import bot.entities.LocationEntity;
import bot.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    List<LocationEntity> findAllByName(String name);

}
