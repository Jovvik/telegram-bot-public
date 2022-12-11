package bot.services;

import bot.entities.GenreEntity;
import bot.repositories.GenreRepository;
import bot.services.GenreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GenreServiceTest {
	private GenreRepository genreRepository;

	@BeforeEach
	public void init() {
		genreRepository = Mockito.mock(GenreRepository.class);
	}

	@Test
	public void saveTest() {
		GenreService service = new GenreService(genreRepository);
		String name = "privet";
		GenreEntity entity = new GenreEntity();
		when(genreRepository.findByName(name)).thenReturn(Optional.of(entity));
		when(genreRepository.save(any())).then(returnsFirstArg());
		Assertions.assertEquals(entity, service.save(entity));
		Assertions.assertEquals(entity, service.findByName(name).get());
	}
}
