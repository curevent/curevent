package com.curevent.services;

import com.curevent.models.entities.EventTagEntity;
import com.curevent.models.entities.TemplateTagEntity;
import com.curevent.repositories.EventTagRepository;
import com.curevent.repositories.TemplateTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TemplateTagService {

    private final TemplateTagRepository templateTagRepository;

    @Autowired
    public TemplateTagService(TemplateTagRepository templateTagRepository) {
        this.templateTagRepository = templateTagRepository;
    }

    public List<TemplateTagEntity> getByEventId(UUID templateId) {
        return templateTagRepository.findByTemplateId(templateId);
    }
    public void add(TemplateTagEntity eventTagEntity) {
        templateTagRepository.save(eventTagEntity);
    }
}
