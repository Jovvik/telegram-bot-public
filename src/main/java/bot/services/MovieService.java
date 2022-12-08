package bot.services;

import bot.entities.GenreEntity;
import bot.entities.MovieEntity;
import bot.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieEntity> findByGenres(Set<GenreEntity> genreEntities) {
        return movieRepository.findByGenresIn(genreEntities);
    }

    public MovieEntity save(MovieEntity movie) {
        return movieRepository.save(movie);
    }

}
