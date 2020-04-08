package com.curevent.services;

import com.curevent.models.entities.Event;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.repositories.EventRepository;
import com.curevent.repositories.RelationshipRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class TimelineService {
    @Autowired
    private final EventRepository eventRepository;
    @Autowired
    private final RelationshipRepository relationshipRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ModelMapper mapper;

    private List<Event> getEventEntitiesInInterval(UUID id, Long interval) {
        long intervalInMills = TimeUnit.MINUTES.toMillis(interval);
        Timestamp startTime = new Timestamp(System.currentTimeMillis() - intervalInMills);
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + intervalInMills);
        return eventRepository.findByOwnerIdAndTimeBetween(id, startTime, endTime);
    }

    public List<EventTransfer> getEventsInInterval(UUID id, Long interval) {
        return getEventEntitiesInInterval(id, interval).stream()
                .map(event -> mapper.map(event, EventTransfer.class))
                .collect(Collectors.toList());
    }

    public List<EventTransfer> getFriendsEventsInInterval(UUID id, Long interval) {
        List <UserTransfer> friends = userService.getUserFriends(id);
        return friends.stream()
                .flatMap(friend -> this.getEventEntitiesInInterval(friend.getId(), interval).stream())
                .filter(event -> checkAccess(id, event))
                .map(event -> mapper.map(event, EventTransfer.class))
                .collect(Collectors.toList());
    }

    private boolean checkAccess(UUID userId, Event event) {
        List <UUID> blackId = event.getBlackList().stream()
                .map(UserEntity::getId)
                .collect(Collectors.toList());
        if (blackId.contains(userId)) {
           return false;
        }
        return relationshipRepository.findFriendCategories(event.getOwnerId(), userId)
                .stream()
                .anyMatch(category -> event.getPrivacy().contains(category));
    }
}
