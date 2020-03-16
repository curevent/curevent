package com.curevent.services;

import com.curevent.models.entities.EventTagEntity;
import com.curevent.models.entities.RelationshipEntity;
import com.curevent.models.entities.TemplateTagEntity;
import com.curevent.repositories.EventTagRepository;
import com.curevent.repositories.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EventTagService {

    private final EventTagRepository eventTagRepository;

    @Autowired
    public EventTagService(EventTagRepository eventTagRepository) {
        this.eventTagRepository = eventTagRepository;
    }

    public List<EventTagEntity> getByEventId(UUID ownerId) {
        return eventTagRepository.findByEventId(ownerId);
    }
    public void add(EventTagEntity eventTagEntity) {
        eventTagRepository.save(eventTagEntity);
    }
}
