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
    private final EventService eventService;

    @Autowired
    public TemplateService(TemplateRepository templateRepository, EventService eventService) {
        this.templateRepository = templateRepository;
        this.eventService = eventService;
    }


    public Template getOneById(UUID id) {
        return templateRepository.findById(id).stream().findAny()
                .orElseThrow(() ->  new TemplateNotFoundException(id));
    }

    public Template add(Template template) {
        return templateRepository.save(template);
    }

    public Template update(Template template) {
        Template curTemplate = getOneById(template.getId());
        curTemplate.setRepeatTime(template.getRepeatTime());
        curTemplate.setDuration(template.getDuration());
        curTemplate.setDescription(template.getDescription());
        curTemplate.setTitle(template.getTitle());
        curTemplate.setPrivacy(template.getPrivacy());
        curTemplate.setTags(new ArrayList<>(template.getTags()));
        templateRepository.save(curTemplate);
        if (!curTemplate.getEvents().isEmpty()) {
            updateEvents(curTemplate);
        }
        return templateRepository.getOne(curTemplate.getId());
    }

    public void delete(UUID id) {
        Template template = getOneById(id);
        if (!template.getEvents().isEmpty()) {
            deleteEvents(id);
        }
        templateRepository.delete(template);
    }

    public List<Event> getEvents(UUID id) {
        Template template = getOneById(id);
        return template.getEvents();
    }

    public void deleteEvents(UUID templateID) {
        Template template = getOneById(templateID);
        template.getEvents().forEach((event)->eventService.delete(event.getId()));
    }

    public void updateEvents(Template template) {
        Timestamp firstAppearanceTime = template.getEvents().get(0).getTime();
        deleteEvents(template.getId());
        createEvents(template.getId(), firstAppearanceTime);
    }

    public List<Event> createEvents(UUID id, Timestamp firstAppearanceTime) {
        Template template = getOneById(id);
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
                .build(), event -> Event.builder()
                        .ownerId(template.getOwnerId())
                        .time(new Timestamp(event.getTime().getTime() + template.getRepeatTime()*60000))
                        .duration(template.getDuration())
                        .title(template.getTitle())
                        .description(template.getDescription())
                        .templateId(template.getId())
                        .privacy(template.getPrivacy())
                        .tags(new ArrayList<>(template.getTags()))
                        .build())
            .limit(template.getRepeatAmount())
            .peek(eventService::add)
            .collect(Collectors.toList());
    }
}
