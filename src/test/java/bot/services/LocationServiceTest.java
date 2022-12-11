package bot.services;

import bot.entities.GenreEntity;
import bot.entities.LocationEntity;
import bot.repositories.GenreRepository;
import bot.repositories.LocationRepository;

import bot.services.LocationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LocationServiceTest {

	private LocationRepository locationRepository;

	@BeforeEach
	public void init() {
		locationRepository = Mockito.mock(LocationRepository.class);
	}

	@Test
	public void saveTest() {
		LocationService service = new LocationService(locationRepository);
		LocationEntity entity = new LocationEntity();
		when(locationRepository.save(any())).then(returnsFirstArg());
		Assertions.assertEquals(entity, service.save(entity));
	}

	@Test
	public void getAllLocationsTest() {
		LocationService service = new LocationService(locationRepository);
		LocationEntity entity1 = new LocationEntity();
		LocationEntity entity2 = new LocationEntity();
		List<LocationEntity> entities = List.of(entity1, entity2);
		when(locationRepository.findAll()).thenReturn(entities);

		List<LocationEntity> serviceLocations = service.getAllLocations();

		for (int i = 0; i < entities.size(); ++i) {
			Assertions.assertEquals(entities.get(i), serviceLocations.get(i));
		}
	}
}
