package com.curevent.services.repeatServices;

import com.curevent.annotations.RepeatServiceFor;
import com.curevent.models.entities.Repeat;
import com.curevent.models.entities.Template;
import com.curevent.models.enums.RepeatType;
import com.curevent.repositories.TemplateRepository;
import com.curevent.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RepeatServiceFor(type = RepeatType.NONE)
public class NoneRepeatService extends RepeatService {
    @Autowired
    public NoneRepeatService(TemplateService templateService, TemplateRepository templateRepository) {
        super(templateService, templateRepository);
    }

    @Override
    public Template parseRepeatForm(Template template, Repeat repeat) {
        template.getEvents().add(createEvent(template, repeat.getStartTime()));
        return templateRepository.save(template);
    }
}
