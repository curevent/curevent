package com.curevent.services;

import com.curevent.exceptions.ConflictException;
import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.UserEntity;
import com.curevent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final TemplateService templateService;
    private final EventService eventService;
    private final CommentService commentService;
    private final RelationshipService relationshipService;

    @Autowired
    public UserService(UserRepository userRepository, TemplateService templateService, EventService eventService, CommentService commentService, RelationshipService relationshipService) {
        this.userRepository = userRepository;
        this.templateService = templateService;
        this.eventService = eventService;
        this.commentService = commentService;
        this.relationshipService = relationshipService;
    }

    public UserEntity getOneById(UUID id) {
        return userRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such User " + id));
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
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ConflictException("User  " + user.getUsername() + " already exists");
        }
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

    public UserEntity update(UserEntity userEntity) {
        UserEntity curUser = getOneById(userEntity.getId());
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userEntity.getUsername());
        if (optionalUser.isPresent() && optionalUser.get().getId() != userEntity.getId()) {
            throw new ConflictException("User  " + userEntity.getUsername() + " already exists");
        }
        curUser.setUsername(userEntity.getUsername());
        curUser.setEmail(userEntity.getEmail());
        curUser.setName(userEntity.getName());
        curUser.setSurname(userEntity.getSurname());
        curUser.setCity(userEntity.getCity());
        curUser.setCountry(userEntity.getCountry());
        curUser.setStatus(userEntity.getStatus());
        return userRepository.save(curUser);
    }

    public void delete(UUID id) {
        UserEntity userEntity = getOneById(id);
        userRepository.delete(userEntity);
    }

    public void deleteTemplates(UUID id) {
        UserEntity user = getOneById(id);
        user.getTemplates().forEach((template) -> templateService.delete(template.getId()));
    }

    public void deleteEvents(UUID id) {
        UserEntity user = getOneById(id);
        user.getEvents().forEach((event) -> eventService.delete(event.getId()));
    }

    public void deleteComments(UUID id) {
        UserEntity user = getOneById(id);
        user.getComments().forEach((comment) -> commentService.delete(comment.getId()));
    }

    public void deleteRelationships(UUID id) {
        UserEntity user = getOneById(id);
        user.getRelationships().forEach((relationship) -> relationshipService.delete(relationship.getId()));
    }
}
