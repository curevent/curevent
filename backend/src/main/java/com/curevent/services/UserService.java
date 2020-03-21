package com.curevent.services;

import com.curevent.exceptions.UserNotFoundException;
import com.curevent.models.entities.Relationship;
import com.curevent.models.entities.UserEntity;
import com.curevent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getOneById(UUID id) {
        return userRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserEntity getOneByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User by email " + email + " not found"));
    }

    public UserEntity getOneByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User by username " + username + " not found"));
    }

    public UserEntity add(UserEntity user) {
        return userRepository.save(user);
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public List <UserEntity> getUserFriends(UUID id) {
        UserEntity user = getOneById(id);
        return user
                .getRelationships()
                .stream()
                .map((relationship) -> userRepository.getOne(relationship.getFriendId()))
                .collect(Collectors.toList());
    }
}
