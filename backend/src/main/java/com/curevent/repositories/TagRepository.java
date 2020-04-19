package com.curevent.repositories;

import com.curevent.models.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query(value = "select t from Tag t where t.ownerId = :ownerId AND t.description = :description")
    Optional<Tag> findEqualsTag(@Param("ownerId")UUID ownerId, @Param("description") String description);
}
