package com.curevent.services;

import com.curevent.exceptions.TemplateNotFoundException;
import com.curevent.exceptions.UserNotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Tag;
import com.curevent.models.entities.Template;
import com.curevent.models.entities.UserEntity;
import com.curevent.repositories.EventRepository;
import com.curevent.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final EventRepository eventRepository;

    @Autowired
    public TemplateService(TemplateRepository templateRepository, EventRepository eventRepository) {
        this.templateRepository = templateRepository;
        this.eventRepository = eventRepository;
    }

    public List<Template> getAllByOwnerId(UUID ownerId) {
        return templateRepository.findByOwnerId(ownerId);
    }

    public Template getOneById(UUID id) {
        return templateRepository.findById(id).stream().findAny().orElse(null);
    }

    public Template add(Template template) {
        return templateRepository.save(template);
    }

    public List<Event> createEvents(UUID id, Timestamp firstAppearanceTime) {
        Template template = templateRepository.findById(id).stream().findAny().orElse(null);
        if (template == null) {
            throw new TemplateNotFoundException(id);
        }
        if (template.getRepeatAmount() == null) {
            template.setRepeatAmount(1);
        }
        if (template.getDescription() == null) {
            //кидаем исключение
        }
        return Stream.iterate(Event.builder()
                .ownerId(template.getOwnerId())
                .time(firstAppearanceTime)
                .duration(template.getDuration())
                .title(template.getTitle())
                .description(template.getDescription())
                .templateId(template.getId())
                .privacy(template.getPrivacy())
                .tags(new ArrayList<>(template.getTags()))
                .build(), n -> Event.builder()
                        .ownerId(template.getOwnerId())
                        .time(new Timestamp(n.getTime().getTime() + template.getRepeatTime()*60000))
                        .duration(template.getDuration())
                        .title(template.getTitle())
                        .description(template.getDescription())
                        .templateId(template.getId())
                        .privacy(template.getPrivacy())
                        .tags(new ArrayList<>(template.getTags()))
                        .build())
            .limit(template.getRepeatAmount())
            .map(eventRepository::save)
            .collect(Collectors.toList());
    }
}
