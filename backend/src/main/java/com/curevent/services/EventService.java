package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapper mapper;

    private Event getEntityById(UUID id) {
        return eventRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Event" + id));
    }

    public EventTransfer getOneById(UUID id) {
        Event event = eventRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Event"+id));
        return mapper.map(event, EventTransfer.class);
    }

    public EventTransfer add(EventTransfer eventTransfer) {
        Event event = mapper.map(eventTransfer, Event.class);
        return mapper.map(eventRepository.save(event), EventTransfer.class);
    }

    public void delete(UUID id) {
        Event event = getEntityById(id);
        eventRepository.delete(event);
    }

    public EventTransfer update(EventTransfer eventTransfer) {
        Event event = mapper.map(eventTransfer, Event.class);
        if (!eventRepository.existsById(event.getId())) {
            throw new NotFoundException("No such Event" + event.getId());
        }
        return mapper.map(eventRepository.save(event), EventTransfer.class);
    }
}
