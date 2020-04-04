package com.curevent.services;

import com.curevent.exceptions.ConflictException;
import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.repositories.UserRepository;
import com.curevent.utils.mapping.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TemplateService templateService;
    @Autowired
    private final EventService eventService;
    @Autowired
    private final CommentService commentService;
    @Autowired
    private final RelationshipService relationshipService;
    @Autowired
    private final UserMapper userMapper;

    private UserEntity getEntityById(UUID id) {
        return userRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such User " + id));
    }

    public UserTransfer getOneById(UUID id) {
        UserEntity user = userRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such User " + id));
        return userMapper.toTransfer(user);
    }

    public UserTransfer add(UserTransfer userTransfer) {
        UserEntity user = userMapper.toEntity(userTransfer);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ConflictException("User  " + user.getUsername() + " already exists");
        }
        return userMapper.toTransfer(userRepository.save(user));
    }

    public List<UserTransfer> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toTransfer)
                .collect(Collectors.toList());
    }

    public List <UserTransfer> getUserFriends(UUID id) {
        UserEntity user = getEntityById(id);
        return user
                .getRelationships()
                .stream()
                .map((relationship) -> userRepository.getOne(relationship.getFriendId()))
                .map(userMapper::toTransfer)
                .collect(Collectors.toList());
    }

    public UserTransfer update(UserTransfer userTransfer) {
        UserEntity userEntity = userMapper.toEntity(userTransfer);
        UserEntity curUser = getEntityById(userEntity.getId());
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
        return userMapper.toTransfer(userRepository.save(curUser));
    }

    public void delete(UUID id) {
        UserEntity userEntity = getEntityById(id);
        userRepository.delete(userEntity);
    }

    public void deleteTemplates(UUID id) {
        UserEntity user = getEntityById(id);
        user.getTemplates().forEach((template) -> templateService.delete(template.getId()));
    }

    public void deleteEvents(UUID id) {
        UserEntity user = getEntityById(id);
        user.getEvents().forEach((event) -> eventService.delete(event.getId()));
    }

    public void deleteComments(UUID id) {
        UserEntity user = getEntityById(id);
        user.getComments().forEach((comment) -> commentService.delete(comment.getId()));
    }

    public void deleteRelationships(UUID id) {
        UserEntity user = getEntityById(id);
        user.getRelationships().forEach((relationship) -> relationshipService.delete(relationship.getId()));
    }
}
