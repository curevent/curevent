package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final CommentService commentService;

    @Autowired
    public EventService(EventRepository eventRepository, CommentService commentService) {
        this.eventRepository = eventRepository;
        this.commentService = commentService;
    }


    public Event getOneById(UUID id) {
        return eventRepository.findById(id).stream().findAny()
                .orElseThrow(()->new NotFoundException("No such Event"+id));
    }


    public Event add(Event event) {
        return eventRepository.save(event);
    }

    public Event update(Event event) {
        if (!eventRepository.existsById(event.getId())) {
            throw new NotFoundException("No such Event"+event.getId());
        }
        return eventRepository.save(event);
    }

    public void delete(UUID id) {
        Event event = getOneById(id);
        eventRepository.delete(event);
    }

    public void deleteComments(UUID id) {
        Event event = getOneById(id);
        event.getComments().forEach((comment) -> commentService.delete(comment.getId()));
    }
}
