package com.curevent.services;

import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.repositories.TemplateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
@Transactional
public class EventFactoryService {
    @Autowired
    private final TemplateService templateService;
    @Autowired
    private final TemplateRepository templateRepository;
    @Autowired
    private final ModelMapper mapper;

    public TemplateTransfer deleteEvents(UUID templateID) {
        Template template = templateService.getEntityById(templateID);
        template.getEvents().clear();
        return mapper.map(templateRepository.save(template), TemplateTransfer.class);
    }

    public TemplateTransfer updateEvents(TemplateTransfer templateTransfer) {
        Template template = mapper.map(templateTransfer, Template.class);
        if (template.getEvents() != null) {
            for (Event event : template.getEvents()) {
                fillEvent(event, template);
            }
            return mapper.map(templateRepository.save(template), TemplateTransfer.class);
        }
        return templateTransfer;
    }

    private void fillEvent(Event base, Template source) {
        base.setOwnerId(source.getOwnerId());
        base.setDuration(source.getDuration());
        base.setTemplateId(source.getId());
        base.setDescription(source.getDescription());
        base.setTitle(source.getTitle());
        base.setPrivacy(new ArrayList<>(source.getPrivacy()));
        base.setTags(new ArrayList<>(source.getTags()));
    }

    public TemplateTransfer createEvents(UUID id, Timestamp startTime) {
        Template template = templateService.getEntityById(id);
        long intervalInMills = TimeUnit.MINUTES.toMillis(template.getRepeatTime());
        template.getEvents().addAll(Stream.iterate(startTime, time -> new Timestamp(time.getTime() + intervalInMills))
                .limit(template.getRepeatAmount())
                .map(time -> {
                    Event event = new Event();
                    event.setTime(time);
                    return event;
                })
                .peek(event -> fillEvent(event, template))
                .collect(Collectors.toList()));
        return mapper.map(templateRepository.save(template), TemplateTransfer.class);
    }
}
