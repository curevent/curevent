package com.curevent.repositories;

import com.curevent.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query(value = "select e from Event e where e.ownerId = :ownerId AND e.time BETWEEN :startTime AND :endTime")
    List<Event> findByOwnerIdAndTimeBetween(@Param("ownerId")UUID ownerId,
                                            @Param("startTime")Timestamp startTime,
                                            @Param("endTime")Timestamp endTime);

    Optional<List<Event>> findByOwnerId(UUID ownerId);
}
