package bot.services;

import bot.entities.TagEntity;
import bot.repositories.TagRepository;
import bot.services.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TagServiceTest {
	private TagRepository tagRepository;

	@BeforeEach
	public void init() {
		tagRepository = Mockito.mock(TagRepository.class);
	}

	@Test
	public void saveTest() {
		TagService service = new TagService(tagRepository);
		String name = "privet";
		TagEntity entity = new TagEntity();
		when(tagRepository.findByName(name)).thenReturn(Optional.of(entity));
		when(tagRepository.save(any())).then(returnsFirstArg());
		Assertions.assertEquals(entity, service.save(entity));
		Assertions.assertEquals(entity, service.findByName(name).get());
	}
}
