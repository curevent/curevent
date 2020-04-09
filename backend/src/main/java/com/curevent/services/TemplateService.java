package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.repositories.TemplateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
}
