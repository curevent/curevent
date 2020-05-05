package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.repositories.TemplateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final ModelMapper mapper;

    Template getEntityById(UUID id) {
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
        return mapper.map(templateRepository.save(template), TemplateTransfer.class);
    }

    public TemplateTransfer update(TemplateTransfer templateTransfer) {
        Template template = mapper.map(templateTransfer, Template.class);
        if (!templateRepository.existsById(template.getId())) {
            throw new NotFoundException("No such Template"+template.getId());
        }
        return mapper.map(templateRepository.save(template), TemplateTransfer.class);
    }

    public void delete(UUID id) {
        Template template = getEntityById(id);
        templateRepository.delete(template);
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

    public void fillEvent(Event base, Template source) {
        base.setOwnerId(source.getOwnerId());
        base.setDuration(source.getDuration());
        base.setTemplateId(source.getId());
        base.setDescription(source.getDescription());
        base.setTitle(source.getTitle());
        base.setPrivacy(new ArrayList<>(source.getPrivacy()));
        base.setTags(new ArrayList<>(source.getTags()));
    }
}
