package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.repositories.TemplateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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

@AllArgsConstructor
@Service
@Transactional
public class TemplateService {

    public static final int DEFAULT_REPEAT_AMOUNT = 1;
    public static final long DEFAULT_REPEAT_TIME = 0L;
    @Autowired
    private final TemplateRepository templateRepository;
    @Autowired
    private final ModelMapper mapper;

    private Template getEntityById(UUID id) {
        return templateRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Template " + id));
    }

    public TemplateTransfer getOneById(UUID id) {
        Template template = templateRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Template"+id));
        return mapper.map(template, TemplateTransfer.class);
    }

    public TemplateTransfer add(TemplateTransfer templateTransfer) {
        Template template = mapper.map(templateTransfer, Template.class);
        validateTemplate(template);
        return mapper.map(templateRepository.save(template), TemplateTransfer.class);
    }

    private void validateTemplate(Template template) {
        if (template.getRepeatAmount() == null) {
            template.setRepeatAmount(DEFAULT_REPEAT_AMOUNT);
        }
        if (template.getRepeatTime() == null) {
            template.setRepeatTime(DEFAULT_REPEAT_TIME);
        }
    }

    public TemplateTransfer update(TemplateTransfer templateTransfer) {
        Template template = mapper.map(templateTransfer, Template.class);
        if (!templateRepository.existsById(template.getId())) {
            throw new NotFoundException("No such Template"+template.getId());
        }
        validateTemplate(template);
        return mapper.map(templateRepository.save(template), TemplateTransfer.class);
    }

    public void delete(UUID id) {
        Template template = getEntityById(id);
        templateRepository.delete(template);
    }

    public List<EventTransfer> getEvents(UUID id) {
        Template template = getEntityById(id);
        return template.getEvents().stream()
                .map(event -> mapper.map(event, EventTransfer.class))
                .collect(Collectors.toList());
    }

    public TemplateTransfer deleteEvents(UUID templateID) {
        Template template = getEntityById(templateID);
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
        Template template = getEntityById(id);
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
