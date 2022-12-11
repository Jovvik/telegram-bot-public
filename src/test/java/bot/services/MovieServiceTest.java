package bot.services;

import bot.backend.nodes.events.MovieEvent;
import bot.entities.GenreEntity;
import bot.entities.MovieEntity;
import bot.repositories.LocationRepository;
import bot.repositories.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

	private MovieRepository movieRepository;

	@BeforeEach
	public void init() {
		movieRepository = Mockito.mock(MovieRepository.class);
	}

	@Test
	public void saveTest() {
		MovieService service = new MovieService(movieRepository);
		MovieEntity entity = new MovieEntity();
		when(movieRepository.save(any())).then(returnsFirstArg());
		Assertions.assertEquals(entity, service.save(entity));
	}

	@Test
	public void findByStartEndAndGenresTest() {
		MovieService service = new MovieService(movieRepository);
		GenreEntity genreEntityCrime = new GenreEntity();
		genreEntityCrime.id = 1L;
		genreEntityCrime.name = "crime";
		GenreEntity genreEntityDocumentary = new GenreEntity();
		genreEntityDocumentary.id = 2L;
		genreEntityDocumentary.name = "documentary";

		MovieEntity entity1 = new MovieEntity();
		entity1.startTime = 100;
		entity1.runningTime = 50;
		entity1.genres = Set.of(genreEntityCrime);

		MovieEntity entity2 = new MovieEntity();
		entity2.startTime = 110;
		entity2.runningTime = 100;
		entity2.genres = Set.of(genreEntityCrime);

		MovieEntity entity3 = new MovieEntity();
		entity3.startTime = 110;
		entity3.runningTime = 20;
		entity3.genres = Set.of(genreEntityDocumentary);

		when(movieRepository.findByStartTimeGreaterThanEqual(any())).thenReturn(List.of(entity1, entity3));
		Assertions.assertEquals(entity1, service.findByStartEndAndGenres(120, 200, Set.of(genreEntityCrime)).get(0));
	}

}
