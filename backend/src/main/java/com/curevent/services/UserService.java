package com.curevent.services;

import com.curevent.models.entities.UserEntity;
import com.curevent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getOneById(UUID id) {
        return userRepository.findById(id).stream().findAny().orElse(null);
    }

    public UserEntity getOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity getOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void add(UserEntity user) {
        userRepository.save(user);
    }
}
