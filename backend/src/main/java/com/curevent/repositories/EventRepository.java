package com.curevent.repositories;

import com.curevent.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findAllByOwnerIdAndTimeGreaterThanEqualAndTimeLessThanEqual(UUID id, Timestamp startTime, Timestamp endTime);
}
