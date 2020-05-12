package com.curevent.repositories;

import com.curevent.models.entities.Category;
import com.curevent.models.entities.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, UUID> {
    List<Relationship> findByOwnerId(UUID owner_id);

    @Query(value = "select r from Relationship r " +
            "where r.ownerId = :ownerId AND r.friendId = :friendId AND r.category.id = :categoryId")
    Optional<Relationship> findEqualsRelationship(@Param("ownerId")UUID ownerId,
                                                  @Param("friendId") UUID friendId,
                                                  @Param("categoryId") Long categoryId);

    @Query(value = "select r.category from Relationship r " +
            "where r.ownerId = :ownerId AND r.friendId = :friendId")
    List<Category> findFriendCategories(@Param("ownerId")UUID ownerId, @Param("friendId") UUID friendId);
}
