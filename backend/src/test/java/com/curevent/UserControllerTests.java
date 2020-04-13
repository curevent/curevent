package com.curevent;

import com.curevent.controllers.*;
import com.curevent.exceptions.ConflictException;
import com.curevent.models.forms.RegisterForm;
import com.curevent.models.forms.RepeatForm;
import com.curevent.models.transfers.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTests {
    public static final String USERNAME = "test";
    public static final String EMAIL = "test@test.com";
    public static final String PASSWORD = "test";
    public static final String CATEGORY = "category";
    public static final String TAG = "tag";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final long DURATION = 100;
    public static final int EVENT_AMOUNT = 2;
    public static final String CITY = "city";
    public static final String NAME = "name";
    public static final String NEW_USERNAME = "test2";
    public static final long INTERVAL = 2880;
    public static final String REPEAT_TYPE = "day";
    public static final int REPEAT_INTERVAL = 1;

    @Resource
    private UserController userController;
    @Resource
    private TemplateController templateController;
    @Resource
    private EventController eventController;
    @Resource
    private AuthController authController;
    @Resource
    private CategoryController categoryController;
    @Resource
    private TagController tagController;
    @Resource
    private RelationshipController relationshipController;

    private CategoryTransfer privacy;
    private TagTransfer tag;

    @BeforeAll
    public void setUp() {
        CategoryTransfer category = new CategoryTransfer();
        category.setDescription(CATEGORY);
        privacy = categoryController.addCategory(category);

        TagTransfer tagTransfer = new TagTransfer();
        tagTransfer.setDescription(TAG);
        tag = tagController.addTag(tagTransfer);
    }

    @Test
    public void putUserTest(){
        UserTransfer user = createUser(USERNAME);
        assertNull(user.getCity());
        assertNull(user.getName());
        user.setCity(CITY);
        user.setName(NAME);
        user = userController.editUser(user);
        assertEquals(CITY, user.getCity());
        assertEquals(NAME, user.getName());

        userController.deleteUser(user.getId());
    }

    @Test
    public void putUserWithAlreadyExistUsername(){
        UserTransfer user = createUser(USERNAME);
        UserTransfer newUser = createUser(NEW_USERNAME);
        user.setUsername(NEW_USERNAME);
        assertThrows(ConflictException.class, () ->
                userController.editUser(user));

        userController.deleteUser(user.getId());
        userController.deleteUser(newUser.getId());
    }

    @Test
    public void getUserFriendsTest(){
        UserTransfer user = createUser(USERNAME);
        UserTransfer friend = createUser(NEW_USERNAME);
        createRelationship(user, friend, privacy);

        List<UserTransfer> friends = userController.getUserFriends(user.getId());
        assertEquals(1, friends.size());
        assertEquals(friend.getId(), friends.get(0).getId());

        userController.deleteUser(user.getId());
        userController.deleteUser(friend.getId());
    }

    @Test
    public void deleteUserFriendsTest(){
        UserTransfer user = createUser(USERNAME);
        UserTransfer friend = createUser(NEW_USERNAME);
        createRelationship(user, friend, privacy);

        List<UserTransfer> friends = userController.getUserFriends(user.getId());
        assertEquals(1, friends.size());
        assertEquals(friend.getId(), friends.get(0).getId());

        user = userController.deleteRelationships(user.getId());
        assertEquals(0, user.getRelationships().size());

        userController.deleteUser(user.getId());
        userController.deleteUser(friend.getId());
    }

    @Test
    public void getUserEventsInIntervalTest(){
        UserTransfer user = createUser(USERNAME);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        createEvents(user, time);
        List <EventTransfer> events = userController.getUserEventsInInterval(user.getId(), INTERVAL);
        assertEvents(events, time);

        userController.deleteUser(user.getId());
    }

    @Test
    public void getUserFriendsEventsInIntervalTest(){
        UserTransfer user = createUser(USERNAME);
        UserTransfer friend = createUser(NEW_USERNAME);
        createRelationship(user, friend, privacy);
        createRelationship(friend, user, privacy);

        Timestamp time = new Timestamp(System.currentTimeMillis());
        createEvents(friend, time);
        List <EventTransfer> events = userController.getUserFriendsEventsInInterval(user.getId(), INTERVAL);
        assertEvents(events, time);

        userController.deleteUser(user.getId());
        userController.deleteUser(friend.getId());
    }

    @Test
    public void getUserFriendsEventsAccessDeniedTest(){
        UserTransfer user = createUser(USERNAME);
        UserTransfer friend = createUser(NEW_USERNAME);
        createRelationship(user, friend, privacy);

        Timestamp time = new Timestamp(System.currentTimeMillis());
        createEvents(friend, time);
        List <EventTransfer> events = userController.getUserFriendsEventsInInterval(user.getId(), INTERVAL);
        assertEquals(0, events.size());

        userController.deleteUser(user.getId());
        userController.deleteUser(friend.getId());
    }

    @Test
    public void getUserFriendsEventsUserInBlackListTest(){
        UserTransfer user = createUser(USERNAME);
        UserTransfer friend = createUser(NEW_USERNAME);
        createRelationship(user, friend, privacy);
        createRelationship(friend, user, privacy);

        Timestamp time = new Timestamp(System.currentTimeMillis());
        TemplateTransfer template = createEvents(friend, time);
        EventTransfer newEvent = template.getEvents().get(0);
        newEvent.setBlackList(List.of(user));
        eventController.editEvent(newEvent);

        List <EventTransfer> events = userController.getUserFriendsEventsInInterval(user.getId(), INTERVAL);
        assertEquals(EVENT_AMOUNT - 1 , events.size());

        userController.deleteUser(user.getId());
        userController.deleteUser(friend.getId());
    }

    private void assertEvents(List <EventTransfer> events, Timestamp time) {
        Timestamp startTime = new Timestamp(time.getTime() - TimeUnit.MINUTES.toMillis(INTERVAL));
        Timestamp endTime = new Timestamp(time.getTime() + TimeUnit.MINUTES.toMillis(INTERVAL));

        assertEquals(EVENT_AMOUNT, events.size());
        assertTrue(events.stream()
                .allMatch(event -> event.getTime().after(startTime) && event.getTime().before(endTime)));
    }

    private TemplateTransfer createEvents(UserTransfer user, Timestamp time) {
        TemplateTransfer templateTransfer = new TemplateTransfer();
        templateTransfer.setOwnerId(user.getId());
        templateTransfer.setTitle(TITLE);
        templateTransfer.setDescription(DESCRIPTION);
        templateTransfer.setDuration(DURATION);
        templateTransfer.setPrivacy(List.of(privacy));
        templateTransfer.setTags(List.of(tag));

        TemplateTransfer template = templateController.addTemplate(templateTransfer);

        RepeatForm repeat = new RepeatForm();
        repeat.setRepeatType(REPEAT_TYPE);
        repeat.setRepeatInterval(REPEAT_INTERVAL);
        repeat.setStartTime(time);
        repeat.setEndTime(Timestamp.valueOf(time.toLocalDateTime().plusDays(EVENT_AMOUNT)));
        return templateController.createEvents(template.getId(), repeat);
    }

    private void createRelationship(UserTransfer user, UserTransfer friend, CategoryTransfer category) {
        RelationshipTransfer relationship = new RelationshipTransfer();
        relationship.setOwnerId(user.getId());
        relationship.setFriendId(friend.getId());
        relationship.setCategory(category);
        relationshipController.addRelationship(relationship);
    }

    private UserTransfer createUser(String username) {
        authController.register(new RegisterForm(username, EMAIL, PASSWORD));
        return userController.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findAny().orElseThrow();
    }

    @AfterAll
    void tearDown() {
        tagController.deleteTag(tag.getId());
        categoryController.deleteCategory(privacy.getId());
    }
}
