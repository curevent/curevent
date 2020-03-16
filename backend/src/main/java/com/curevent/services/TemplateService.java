package com.curevent.services;

import com.curevent.models.entities.TemplateEntity;
import com.curevent.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public TemplateEntity getOneById(UUID id) {
        return templateRepository.findById(id).stream().findAny().orElse(null);
    }

    public void add(TemplateEntity templateEntity) {
        templateRepository.save(templateEntity);
    }
}
