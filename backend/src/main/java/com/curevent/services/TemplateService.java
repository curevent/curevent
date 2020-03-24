package com.curevent.services;

import com.curevent.exceptions.EventNotFoundException;
import com.curevent.exceptions.TemplateNotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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
                .orElseThrow(() -> new TemplateNotFoundException(id));
    }

    public Template add(Template template) {
        return templateRepository.save(template);
    }

    public Template update(Template template) {
        if (!templateRepository.existsById(template.getId())) {
            throw new EventNotFoundException(template.getId());
        }
        return templateRepository.save(template);
    }

    public void delete(UUID id) {
        Template template = getOneById(id);
        templateRepository.delete(template);
    }

    public List<Event> getEvents(UUID id) {
        Template template = getOneById(id);
        return template.getEvents();
    }

    public void deleteEvents(UUID templateID) {
        Template template = getOneById(templateID);
        template.getEvents().forEach((event) -> eventService.delete(event.getId()));
    }

    public void updateEvents(Template template) {
        template.getEvents().forEach(event -> {
            fillEvent(event, template);
            eventService.update(event);
        });
    }

    private static void fillEvent(Event base, Template source) {
        base.setOwnerId(source.getOwnerId());
        base.setDuration(source.getDuration());
        base.setTemplateId(source.getId());
        base.setDescription(source.getDescription());
        base.setTitle(source.getTitle());
        base.setPrivacy(source.getPrivacy());
        base.setTags(new ArrayList<>(source.getTags()));
    }

    public List<Event> createEvents(UUID id, Timestamp firstAppearanceTime) {
        Template template = getOneById(id);
        if (template.getRepeatAmount() == null) {
            template.setRepeatAmount(1);
        }
        if (template.getDescription() == null) {
            //кидаем исключение
        }
        Long intervalInMills = TimeUnit.MINUTES.toMillis(template.getRepeatTime());
        return Stream.iterate(firstAppearanceTime, time -> new Timestamp(time.getTime() + intervalInMills))
                .limit(template.getRepeatAmount())
                .map(time -> {
                    Event event = new Event();
                    event.setTime(time);
                    return event;
                })
                .peek(event -> fillEvent(event, template))
                .peek(eventService::add)
                .collect(Collectors.toList());
    }
}
