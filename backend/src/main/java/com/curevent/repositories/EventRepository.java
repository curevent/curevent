package com.curevent.repositories;

import com.curevent.models.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {

    List<EventEntity> findByOwnerId(UUID ownerId);
}
