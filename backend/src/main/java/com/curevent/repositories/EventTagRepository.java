package com.curevent.repositories;

import com.curevent.models.entities.EventTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventTagRepository extends JpaRepository<EventTagEntity, UUID> {
}
