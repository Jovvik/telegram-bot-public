package bot.services;

import bot.entities.GenreEntity;
import bot.entities.MovieEntity;
import bot.external.kudago.MovieResponse;
import bot.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieEntity> findByGenres(Set<GenreEntity> genreEntities) {
        return movieRepository.findByGenresIn(genreEntities);
    }

    public List<MovieEntity> findByStartTimeGreaterThanEqual(Integer startTime) {
        return movieRepository.findByStartTimeGreaterThanEqual(startTime);

    }

    public List<MovieEntity> findByStartEndAndGenres(Integer startTime, Integer endTime, Set<GenreEntity> genres) {
        List<MovieEntity> entities = findByStartTimeGreaterThanEqual(startTime);

        return entities.stream().filter(it -> it.startTime + it.runningTime <= endTime)
                .filter(it ->
                        it.genres.stream().map(g -> g.name).collect(Collectors.toSet())
                                .containsAll(genres.stream().map(g -> g.name).collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }

    public MovieEntity save(MovieEntity movie) {
        return movieRepository.save(movie);
    }

}
