package com.curevent.services;

import com.curevent.exceptions.ConflictException;
import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Tag;
import com.curevent.models.transfers.TagTransfer;
import com.curevent.repositories.TagRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;
    private final ModelMapper mapper;

    Tag getEntityById(UUID id) {
        return tagRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Tag" + id));
    }

    public TagTransfer getOneById(UUID id) {
        Tag tag = tagRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Tag"+id));
        return mapper.map(tag, TagTransfer.class);
    }

    public TagTransfer add(TagTransfer tagTransfer) {
        Tag tag = mapper.map(tagTransfer, Tag.class);
        if(tagRepository.findEqualsTag(tag.getOwnerId(), tag.getDescription()).isPresent()) {
            throw new ConflictException("Tag with ownerId " + tag.getOwnerId() +
                    "and description " + tag.getDescription() + " already exists");
        }
        return mapper.map(tagRepository.save(tag), TagTransfer.class);
    }

    public void delete(UUID id) {
        Tag tag = getEntityById(id);
        tagRepository.delete(tag);
    }

    public TagTransfer update(TagTransfer tagTransfer) {
        Tag tag = mapper.map(tagTransfer, Tag.class);
        if (!tagRepository.existsById(tag.getId())) {
            throw new NotFoundException("No such Tag" + tag.getId());
        }
        Optional<Tag> equalsTag= tagRepository.findEqualsTag(tag.getOwnerId(), tag.getDescription());
        if(equalsTag.isPresent() && !equalsTag.get().getId().equals(tag.getId())) {
            throw new ConflictException("Tag with ownerId " + tag.getOwnerId() +
                    "and description " + tag.getDescription() + " already exists");
        }
        return mapper.map(tagRepository.save(tag), TagTransfer.class);
    }
}
