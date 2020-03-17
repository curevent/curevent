package com.curevent.services;

import com.curevent.models.entities.Event;
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

    public Event getOneById(UUID id) {
        return eventRepository.findById(id).stream().findAny().orElse(null);
    }


    public List<Event> getAllByOwnerId(UUID ownerId) {
        return eventRepository.findByOwnerId(ownerId);
    }

    public void add(Event event) {
        eventRepository.save(event);
    }
}
