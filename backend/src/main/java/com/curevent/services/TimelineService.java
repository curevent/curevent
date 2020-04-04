package com.curevent.services;

import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.repositories.EventRepository;
import com.curevent.utils.mapping.EventMapper;
import lombok.AllArgsConstructor;
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
    private final UserService userService;
    @Autowired
    private final EventMapper eventMapper;

    public List<EventTransfer> getEventsInInterval(UUID id, Long interval) {
        long intervalInMills = TimeUnit.MINUTES.toMillis(interval);
        Timestamp startTime = new Timestamp(System.currentTimeMillis() - intervalInMills);
        Timestamp endTime = new Timestamp(System.currentTimeMillis() + intervalInMills);
        return eventRepository.findByOwnerIdAndTimeBetween(id, startTime, endTime).stream()
                .map(eventMapper::toTransfer)
                .collect(Collectors.toList());
    }

    public List<EventTransfer> getFriendsEventsInInterval(UUID id, Long interval) {
        List <UserTransfer> friends = userService.getUserFriends(id);
        return friends.stream()
                .flatMap(friend -> this.getEventsInInterval(friend.getId(), interval).stream())
                .collect(Collectors.toList());
    }
}
