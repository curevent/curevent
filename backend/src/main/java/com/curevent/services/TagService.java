package com.curevent.services;

import com.curevent.exceptions.CommentNotFoundException;
import com.curevent.exceptions.TagNotFoundException;
import com.curevent.models.entities.Comment;
import com.curevent.models.entities.Tag;
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

    public Tag getOneById(UUID id) {
        return tagRepository.findById(id).stream().findAny()
                .orElseThrow(()-> new TagNotFoundException(id));
    }

    public Tag add(Tag tag) {
        return tagRepository.save(tag);
    }

    public void delete(UUID id) {
        Tag tag = getOneById(id);
        tagRepository.delete(tag);
    }

    public Tag update(Tag tag) {
        if (!tagRepository.existsById(tag.getId())) {
            throw new TagNotFoundException(tag.getId());
        }
        return tagRepository.save(tag);
    }

}
