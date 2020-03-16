package com.curevent.repositories;

import com.curevent.models.entities.TemplateTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TemplateTagRepository extends JpaRepository<TemplateTagEntity, UUID> {
    List<TemplateTagEntity> findByTemplateId(UUID templateId);
}
