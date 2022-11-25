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

    public TagEntity save(TagEntity tag) {
        Optional<TagEntity> optionalTag = tagRepository.findByName(tag.name);
        return optionalTag.orElseGet(() -> tagRepository.save(tag));
    }

    public Optional<TagEntity> findByName(String tagName) {
        return tagRepository.findByName(tagName);
    }

}
