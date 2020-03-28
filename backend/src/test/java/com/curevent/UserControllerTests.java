package com.curevent;

import com.curevent.controllers.*;
import com.curevent.exceptions.ConflictException;
import com.curevent.models.forms.RegisterForm;
import com.curevent.models.transfers.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    public static final long REPEAT_TIME = 60;
    public static final int REPEAT_AMOUNT = 3;
    public static final String CITY = "city";
    public static final String NAME = "name";
    public static final String NEW_USERNAME = "test2";
    public static final long INTERVAL = 1440;

    @Autowired
    private UserController userController;
    @Autowired
    private TemplateController templateController;
    @Autowired
    private AuthController authController;
    @Autowired
    private CategoryController categoryController;
    @Autowired
    private TagController tagController;
    @Autowired
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
        createRelationship(user, friend);

        List<UserTransfer> friends = userController.getUserFriends(user.getId());
        assertEquals(1, friends.size());
        assertEquals(friend.getId(), friends.get(0).getId());

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
        createRelationship(user, friend);

        Timestamp time = new Timestamp(System.currentTimeMillis());
        createEvents(friend, time);
        List <EventTransfer> events = userController.getUserFriendsEventsInInterval(user.getId(), INTERVAL);
        assertEvents(events, time);

        userController.deleteUser(user.getId());
        userController.deleteUser(friend.getId());
    }

    private void assertEvents(List <EventTransfer> events, Timestamp time) {
        Timestamp startTime = new Timestamp(time.getTime() - TimeUnit.MINUTES.toMillis(INTERVAL));
        Timestamp endTime = new Timestamp(time.getTime() + TimeUnit.MINUTES.toMillis(INTERVAL));

        assertEquals(REPEAT_AMOUNT, events.size());
        assertTrue(events.stream()
                .allMatch(event -> event.getTime().after(startTime) && event.getTime().before(endTime)));
    }

    private void createEvents(UserTransfer user, Timestamp time) {
        TemplateTransfer templateTransfer = new TemplateTransfer();
        templateTransfer.setOwnerId(user.getId());
        templateTransfer.setTitle(TITLE);
        templateTransfer.setDescription(DESCRIPTION);
        templateTransfer.setDuration(DURATION);
        templateTransfer.setRepeatTime(REPEAT_TIME);
        templateTransfer.setRepeatAmount(REPEAT_AMOUNT);
        templateTransfer.setPrivacy(privacy);
        templateTransfer.setTags(new ArrayList<>());
        templateTransfer.getTags().add(tag);

        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        templateController.createEvents(template.getId(), time);
    }

    private void createRelationship(UserTransfer user, UserTransfer friend) {
        RelationshipTransfer relationship = new RelationshipTransfer();
        relationship.setOwnerId(user.getId());
        relationship.setFriendId(friend.getId());
        relationship.setCategory(privacy);
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
