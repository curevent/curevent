package com.curevent.services;

import com.curevent.models.entities.EventEntity;
import com.curevent.models.entities.TemplateEntity;
import com.curevent.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventEntity getOneById(UUID id) {
        return eventRepository.findById(id).stream().findAny().orElse(null);
    }


    public List<EventEntity> getAllByOwnerId(UUID ownerId) {
        return eventRepository.findByOwnerId(ownerId);
    }

    public void add(EventEntity eventEntity) {
        eventRepository.save(eventEntity);
    }
}
