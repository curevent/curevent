package com.curevent;

import com.curevent.models.entities.Category;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.repositories.EventRepository;
import com.curevent.repositories.RelationshipRepository;
import com.curevent.services.TimelineService;
import com.curevent.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimelineServiceTests {
    public static final String USERNAME = "test";
    public static final String NEW_USERNAME = "test2";
    public static final String EMAIL = "test@test.com";
    public static final String NEW_EMAIL = "test@test.com";
    public static final String CATEGORY = "category";
    public static final long CATEGORY_ID = 1L;
    public static final UUID USER_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a57");
    public static final UUID NEW_USER_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a58");
    public static final UUID FIRST_EVENT_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a60");
    public static final UUID SECOND_EVENT_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a60");
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final int EVENT_AMOUNT = 2;
    public static final long INTERVAL = 1440;
    public static final Timestamp FIRST_EVENT_TIME = Timestamp.valueOf("2020-03-20 17:00:00.000");
    public static final Timestamp SECOND_EVENT_TIME = Timestamp.valueOf("2020-03-21 17:00:00.000");


    private List<Event> events;
    private List<Event> friendsEvents;
    private Category category;
    private UserTransfer newUser;
    private UserEntity user;

    @MockBean
    private EventRepository eventRepository;
    @MockBean
    private RelationshipRepository relationshipRepository;
    @MockBean
    private UserService userService;

    @Resource
    private TimelineService timelineService;


    @BeforeAll
    public void setUp() {
        category = new Category(CATEGORY_ID, CATEGORY);

        events = new ArrayList<>();
        events.add(createEvent(FIRST_EVENT_TIME, FIRST_EVENT_ID, USER_ID));
        events.add(createEvent(SECOND_EVENT_TIME, SECOND_EVENT_ID, USER_ID));

        friendsEvents = new ArrayList<>();
        friendsEvents.add(createEvent(FIRST_EVENT_TIME, FIRST_EVENT_ID, NEW_USER_ID));
        friendsEvents.add(createEvent(SECOND_EVENT_TIME, SECOND_EVENT_ID, NEW_USER_ID));

        user = new UserEntity();
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setId(USER_ID);

        newUser = new UserTransfer();
        newUser.setUsername(NEW_USERNAME);
        newUser.setEmail(NEW_EMAIL);
        newUser.setId(NEW_USER_ID);
    }

    @Test
    public void get_two_user_events_in_interval_test(){
        when(eventRepository.findByOwnerIdAndTimeBetween(Mockito.eq(USER_ID),
                Mockito.any(Timestamp.class),
                Mockito.any(Timestamp.class)))
                .thenReturn(events);

        List <EventTransfer> events = timelineService.getEventsInInterval(USER_ID, INTERVAL);
        assertEquals(EVENT_AMOUNT, events.size());
        assertEvents(events, USER_ID);
    }

    @Test
    public void get_two_user_friends_events_in_interval_test(){
        when(eventRepository.findByOwnerIdAndTimeBetween(Mockito.eq(NEW_USER_ID),
                Mockito.any(Timestamp.class),
                Mockito.any(Timestamp.class)))
                .thenReturn(friendsEvents);
        when(userService.getUserFriends(USER_ID)).thenReturn(List.of(newUser));
        when(relationshipRepository.findFriendCategories(NEW_USER_ID, USER_ID)).thenReturn(List.of(category));

        List <EventTransfer> events = timelineService.getFriendsEventsInInterval(USER_ID, INTERVAL);
        assertEquals(EVENT_AMOUNT, events.size());
        assertEvents(events, NEW_USER_ID);
    }

    @Test
    public void get_empty_list_user_friends_events_because_access_denied_test(){
        when(eventRepository.findByOwnerIdAndTimeBetween(Mockito.eq(NEW_USER_ID),
                Mockito.any(Timestamp.class),
                Mockito.any(Timestamp.class)))
                .thenReturn(friendsEvents);
        when(userService.getUserFriends(USER_ID)).thenReturn(List.of(newUser));
        when(relationshipRepository.findFriendCategories(NEW_USER_ID, USER_ID)).thenReturn(new ArrayList<>());

        List <EventTransfer> events = timelineService.getEventsInInterval(USER_ID, INTERVAL);
        assertEquals(0, events.size());
    }

    @Test
    public void get_one_user_friends_event_because_user_in_black_list_test(){
        friendsEvents.get(0).setBlackList(List.of(user));
        when(eventRepository.findByOwnerIdAndTimeBetween(Mockito.eq(NEW_USER_ID),
                Mockito.any(Timestamp.class),
                Mockito.any(Timestamp.class)))
                .thenReturn(friendsEvents);
        when(userService.getUserFriends(USER_ID)).thenReturn(List.of(newUser));
        when(relationshipRepository.findFriendCategories(NEW_USER_ID, USER_ID)).thenReturn(List.of(category));

        List <EventTransfer> events = timelineService.getEventsInInterval(USER_ID, INTERVAL);
        assertEquals(0, events.size());

        friendsEvents.get(0).setBlackList(new ArrayList<>());
    }

    private Event createEvent(Timestamp time, UUID id, UUID ownerId) {
        Event event = new Event();
        event.setTime(time);
        event.setId(id);
        event.setOwnerId(ownerId);
        event.setTitle(TITLE);
        event.setDescription(DESCRIPTION);
        event.setPrivacy(List.of(category));
        event.setBlackList(new ArrayList<>());
        return event;
    }

    private void assertEvents(List <EventTransfer> events, UUID ownerId) {
        assertTrue(events.stream()
                .anyMatch(event -> event.getTime().equals(FIRST_EVENT_TIME) || event.getTime().equals(SECOND_EVENT_TIME)));

        assertTrue(events.stream()
                .anyMatch(event -> event.getId().equals(FIRST_EVENT_ID) || event.getTime().equals(FIRST_EVENT_TIME)));

        events.forEach(event -> assertEvent(event, ownerId));
    }

    private void assertEvent(EventTransfer event, UUID ownerId) {
        assertEquals(ownerId, event.getOwnerId());
        assertEquals(TITLE, event.getTitle());
        assertEquals(DESCRIPTION, event.getDescription());
        assertNull(event.getDuration());
        assertNull(event.getTemplateId());
        assertNull(event.getTags());
        assertNull(event.getComments());

        assertTrue(event.getPrivacy().stream().allMatch(t -> t.getId().equals(CATEGORY_ID)));
    }
}
