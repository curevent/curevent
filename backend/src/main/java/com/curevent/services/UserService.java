package com.curevent.services;

import com.curevent.exceptions.ConflictException;
import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TemplateService templateService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CommentService commentService;
    private final RelationshipService relationshipService;
    private final ModelMapper mapper;

    UserEntity getEntityById(UUID id) {
        return userRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such User " + id));
    }

    public UserTransfer getOneById(UUID id) {
        UserEntity user = userRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such User " + id));
        UserTransfer userTransfer = mapper.map(user, UserTransfer.class);
        userTransfer.getCategories().addAll(categoryService.getDefaultCategories());
        return userTransfer;
    }

    public UserTransfer add(UserTransfer userTransfer) {
        UserEntity user = mapper.map(userTransfer, UserEntity.class);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ConflictException("User  " + user.getUsername() + " already exists");
        }
        return mapper.map(userRepository.save(user), UserTransfer.class);
    }

    public List<UserTransfer> getAll(Long limit, Long offset) {
        return userRepository.findAll().stream()
                .skip(offset)
                .limit(limit)
                .map(user -> mapper.map(user, UserTransfer.class))
                .collect(Collectors.toList());
    }

    public List <UserTransfer> getUserFriends(UUID id) {
        UserEntity userEntity = getEntityById(id);
        return userEntity
                .getRelationships()
                .stream()
                .map((relationship) -> userRepository.getOne(relationship.getFriendId()))
                .map(user -> mapper.map(user, UserTransfer.class))
                .distinct()
                .collect(Collectors.toList());
    }

    public UserTransfer update(UserTransfer userTransfer) {
        UserEntity user = mapper.map(userTransfer, UserEntity.class);
        UserEntity curUser = getEntityById(user.getId());
        Optional<UserEntity> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent() && optionalUser.get().getId() != user.getId()) {
            throw new ConflictException("User  " + user.getUsername() + " already exists");
        }
        validateUser(user, curUser);
        return mapper.map(userRepository.save(user), UserTransfer.class);
    }

    private void validateUser(UserEntity user, UserEntity curUser) {
        user.setPassword(curUser.getPassword());
        user.setRefreshToken(curUser.getRefreshToken());
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

    private List<UserTransfer> findByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.map(entity -> List.of(mapper.map(entity, UserTransfer.class))).orElse(Collections.emptyList());
    }

    private List<UserTransfer> findByNameAndSurname(String name, String surname) {
        Optional<List<UserEntity>> userEntities = userRepository.findByNameAndSurname(name, surname);
        return userEntities.map(entities -> entities.stream()
                .map((userEntity) -> mapper.map(userEntity, UserTransfer.class))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    public List<UserTransfer> findUser(String username, String name, String surname) {
        if (username != null) {
            return findByUsername(username);
        }
        if (name != null && surname != null) {
            return findByNameAndSurname(name, surname);
        }
        return Collections.emptyList();
    }

}
