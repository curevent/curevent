package com.curevent.services;

import com.curevent.models.User;
import com.curevent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOneById(UUID id) {
        return userRepository.findById(id).stream().findAny().orElse(null);
    }

    public User getOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void add(User user) {
        userRepository.save(user);
    }
}
