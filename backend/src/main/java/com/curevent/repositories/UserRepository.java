package com.curevent.repositories;

import com.curevent.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);
    Optional<List<UserEntity>> findByNameAndSurname(String name, String surname);
    boolean existsByUsername(String username);
}