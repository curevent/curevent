package com.curevent.services;

import com.curevent.models.entities.EventEntity;
import com.curevent.models.entities.TagEntity;
import com.curevent.repositories.EventRepository;
import com.curevent.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagEntity getOneById(UUID id) {
        return tagRepository.findById(id).stream().findAny().orElse(null);
    }

    public void add(TagEntity tagEntity) {
        tagRepository.save(tagEntity);
    }
}
