package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.repositories.EventRepository;
import com.curevent.utils.mapping.EventMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class EventService {
    @Autowired
    private final EventRepository eventRepository;
    @Autowired
    private final EventMapper mapper;

    private Event getEntityById(UUID id) {
        return eventRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Event" + id));
    }

    public EventTransfer getOneById(UUID id) {
        Event event = eventRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Event"+id));
        return mapper.toTransfer(event);
    }

    public EventTransfer add(EventTransfer eventTransfer) {
        Event event = mapper.toEntity(eventTransfer);
        return mapper.toTransfer(eventRepository.save(event));
    }

    public void delete(UUID id) {
        Event event = getEntityById(id);
        eventRepository.delete(event);
    }

    public EventTransfer update(EventTransfer eventTransfer) {
        Event event = mapper.toEntity(eventTransfer);
        if (!eventRepository.existsById(event.getId())) {
            throw new NotFoundException("No such Event" + event.getId());
        }
        return mapper.toTransfer(eventRepository.save(event));
    }
}
