package com.curevent.repositories;

import com.curevent.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select c from Category c where c.ownerId = :ownerId AND c.description = :description")
    Optional<Category> findEqualsCategory(@Param("ownerId") UUID ownerId, @Param("description") String description);
}
