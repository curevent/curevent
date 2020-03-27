package com.curevent.services;

import com.curevent.models.entities.Event;
import com.curevent.models.entities.UserEntity;
import com.curevent.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TimelineService {

    private final EventRepository eventRepository;
    private final UserService userService;

    @Autowired
    public TimelineService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public List<Event> getEventsInInterval(UUID id, Long interval) {
        long intervalInMills = TimeUnit.MINUTES.toMillis(interval);
        Timestamp startTime = new Timestamp(System.currentTimeMillis() - intervalInMills);
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + intervalInMills);
        return eventRepository.findByOwnerIdAndTimeBetween(id, startTime, endTime);
    }

    public List<Event> getFriendsEventsInInterval(UUID id, Long interval) {
        List <UserEntity> friends = userService.getUserFriends(id);
        return friends.stream()
                .flatMap(friend -> this.getEventsInInterval(friend.getId(), interval).stream())
                .collect(Collectors.toList());
    }
}
