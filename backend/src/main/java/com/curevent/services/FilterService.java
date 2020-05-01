package com.curevent.services;

import com.curevent.models.entities.Event;
import com.curevent.models.entities.Tag;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TagTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.repositories.EventRepository;
import com.curevent.repositories.TemplateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class FilterService {
    private final EventRepository eventRepository;
    private final TemplateRepository templateRepository;
    private final TimelineService timelineService;
    private final ModelMapper mapper;


    public List<TemplateTransfer> filterTemplatesByTags(UUID ownerId, List<UUID> tagsId) {
        Optional<List<Template>> foundTemplates = templateRepository.findByOwnerId(ownerId);
        return foundTemplates.map(templates -> templates.stream()
                .filter(template -> template.getTags().stream()
                        .map(Tag::getId)
                        .collect(Collectors.toList())
                        .containsAll(tagsId))
                .map(template -> mapper.map(template, TemplateTransfer.class))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public List<EventTransfer> filterEventsByTags(UUID ownerId, List<UUID> tagsId, Long interval) {
        List<EventTransfer> events = Collections.emptyList();
        if(interval != null) {
            events = timelineService.getEventsInInterval(ownerId, interval);
        } else {
            Optional<List<Event>> foundEvents = eventRepository.findByOwnerId(ownerId);
            if (foundEvents.isPresent()){
                events = foundEvents.get().stream()
                        .map(event -> mapper.map(event, EventTransfer.class))
                        .collect(Collectors.toList());
            }
        }
        return filterEventTransfers(events, tagsId);
    }

    public List<EventTransfer> filterFriendsEventsByTags(UUID ownerId, List<UUID> tagsId, Long interval) {
        List<EventTransfer> events = timelineService.getEventsInInterval(ownerId, interval);
        return filterEventTransfers(events, tagsId);
    }

    private List<EventTransfer> filterEventTransfers(List<EventTransfer> events, List<UUID> tagsId){
        return events.stream().filter(event -> event.getTags().stream()
                .map(TagTransfer::getId)
                .collect(Collectors.toList())
                .containsAll(tagsId))
                .map(event -> mapper.map(event, EventTransfer.class))
                .collect(Collectors.toList());
    }
}
