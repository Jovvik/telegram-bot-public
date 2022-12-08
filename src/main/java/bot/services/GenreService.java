package bot.services;

import bot.entities.GenreEntity;
import bot.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    
    public GenreEntity save(GenreEntity tag) {
        Optional<GenreEntity> optionalTag = genreRepository.findByName(tag.name);
        return optionalTag.orElseGet(() -> genreRepository.save(tag));
    }

    public Optional<GenreEntity> findByName(String name) {
        return genreRepository.findByName(name);
    }
}
