package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.repositories.TemplateRepository;
import com.curevent.utils.mapping.EventMapper;
import com.curevent.utils.mapping.TemplateMapper;
import lombok.AllArgsConstructor;
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

    @Autowired
    private final TemplateRepository templateRepository;
    @Autowired
    private final TemplateMapper templateMapper;
    @Autowired
    private final EventMapper eventMapper;

    private Template getEntityById(UUID id) {
        return templateRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Template " + id));
    }

    public TemplateTransfer getOneById(UUID id) {
        Template template = templateRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Template"+id));
        return templateMapper.toTransfer(template);
    }

    public TemplateTransfer add(TemplateTransfer templateTransfer) {
        Template template = templateMapper.toEntity(templateTransfer);
        return templateMapper.toTransfer(templateRepository.save(template));
    }

    public TemplateTransfer update(TemplateTransfer templateTransfer) {
        Template template = templateMapper.toEntity(templateTransfer);
        if (!templateRepository.existsById(template.getId())) {
            throw new NotFoundException("No such Template"+template.getId());
        }
        return templateMapper.toTransfer(templateRepository.save(template));
    }

    public void delete(UUID id) {
        Template template = getEntityById(id);
        templateRepository.delete(template);
    }

    public List<EventTransfer> getEvents(UUID id) {
        Template template = getEntityById(id);
        return template.getEvents().stream()
                .map(eventMapper::toTransfer)
                .collect(Collectors.toList());
    }

    public TemplateTransfer deleteEvents(UUID templateID) {
        Template template = getEntityById(templateID);
        template.getEvents().clear();
        return templateMapper.toTransfer(templateRepository.save(template));
    }

    public TemplateTransfer updateEvents(TemplateTransfer templateTransfer) {
        Template template = templateMapper.toEntity(templateTransfer);
        if (template.getEvents() != null) {
            for (Event event : template.getEvents()) {
                fillEvent(event, template);
            }
            return templateMapper.toTransfer(templateRepository.save(template));
        }
        return templateTransfer;
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

    public TemplateTransfer createEvents(UUID id, Timestamp startTime) {
        Template template = getEntityById(id);
        if (template.getRepeatAmount() == null) {
            template.setRepeatAmount(1);
        }
        if (template.getRepeatTime() == null) {
            template.setRepeatTime((long)0);
        }
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
        return templateMapper.toTransfer(templateRepository.save(template));
    }
}
