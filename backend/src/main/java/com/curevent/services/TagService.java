package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Tag;
import com.curevent.models.transfers.TagTransfer;
import com.curevent.repositories.TagRepository;
import com.curevent.utils.mapping.TagMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class TagService {
    @Autowired
    private final TagRepository tagRepository;
    @Autowired
    private final TagMapper mapper;

    private Tag getEntityById(UUID id) {
        return tagRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Tag" + id));
    }

    public TagTransfer getOneById(UUID id) {
        Tag tag = tagRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Tag"+id));
        return mapper.toTransfer(tag);
    }

    public TagTransfer add(TagTransfer tagTransfer) {
        Tag tag = mapper.toEntity(tagTransfer);
        return mapper.toTransfer(tagRepository.save(tag));
    }

    public void delete(UUID id) {
        Tag tag = getEntityById(id);
        tagRepository.delete(tag);
    }

    public TagTransfer update(TagTransfer tagTransfer) {
        Tag tag = mapper.toEntity(tagTransfer);
        if (!tagRepository.existsById(tag.getId())) {
            throw new NotFoundException("No such Tag" + tag.getId());
        }
        return mapper.toTransfer(tagRepository.save(tag));
    }
}
