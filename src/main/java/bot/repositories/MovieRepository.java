package bot.repositories;

import bot.entities.GenreEntity;
import bot.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findByGenresIn(Set<GenreEntity> genreEntities);

    List<MovieEntity> findByStartTimeGreaterThanEqual(Integer startTime);

}
