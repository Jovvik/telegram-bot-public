package bot.services;

import bot.entities.LocationEntity;
import bot.entities.TagEntity;
import bot.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Optional<TagEntity> findTagById(Long id) {
        return tagRepository.findById(id);
    }

    public void save(TagEntity tag) {
        tagRepository.save(tag);
    }

    public List<LocationEntity> findAllByName(String name) {
        return tagRepository.findAllByName(name);
    }

}
