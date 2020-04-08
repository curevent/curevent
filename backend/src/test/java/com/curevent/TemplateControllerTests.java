package com.curevent;

import com.curevent.controllers.*;
import com.curevent.models.forms.RegisterForm;
import com.curevent.models.transfers.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TemplateControllerTests {

    public static final String TEST_USERNAME = "test";
    public static final String TEST_EMAIL = "test@test.com";
    public static final String TEST_PASSWORD = "test";
    public static final String CATEGORY = "category";
    public static final String TAG = "tag";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final long DURATION = 100;
    public static final long REPEAT_TIME = 60;
    public static final String NEW_TITLE = "new_title";
    public static final long NEW_DURATION = 1000;
    public static final int REPEAT_AMOUNT = 3;
    public static final String NEW_TAG = "new_tag";

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

    private UUID userID;
    private CategoryTransfer privacy;
    private TagTransfer tag;
    private Timestamp time;


    @BeforeAll
    public void setUp() {
        authController.register(new RegisterForm(TEST_USERNAME, TEST_EMAIL, TEST_PASSWORD));
        UserTransfer user = userController.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(TEST_USERNAME))
                .findAny().orElseThrow();
        userID = user.getId();

        CategoryTransfer category = new CategoryTransfer();
        category.setDescription(CATEGORY);
        privacy = categoryController.addCategory(category);

        TagTransfer tagTransfer = new TagTransfer();
        tagTransfer.setDescription(TAG);
        tag = tagController.addTag(tagTransfer);

        time = Timestamp.valueOf("2020-03-27 17:00:00.000");
    }

    @Test
    void createEventsTest() {
        TemplateTransfer templateTransfer = createTemplate(REPEAT_TIME, REPEAT_AMOUNT);
        TemplateTransfer template = templateController.addTemplate(templateTransfer);

        List<EventTransfer> events = templateController.createEvents(template.getId(), time).getEvents();
        assertEquals(REPEAT_AMOUNT, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(time)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-03-27 18:00:00.000"))));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-03-27 19:00:00.000"))));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createEventsWithoutRepeatAmountTest() {
        TemplateTransfer templateTransfer = createTemplate(REPEAT_TIME, null);
        TemplateTransfer template = templateController.addTemplate(templateTransfer);

        List<EventTransfer> events = templateController.createEvents(template.getId(), time).getEvents();
        assertEquals(1, events.size());
        events.forEach(event -> assertEvent(event, template));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createEventsWithoutRepeatTimeTest() {
        TemplateTransfer templateTransfer = createTemplate(null, REPEAT_AMOUNT);
        TemplateTransfer template = templateController.addTemplate(templateTransfer);

        List<EventTransfer> events = templateController.createEvents(template.getId(), time).getEvents();
        assertEquals(REPEAT_AMOUNT, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().allMatch(e -> e.getTime().equals(time)));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void editTemplateWithEventsTest() {
        TemplateTransfer templateTransfer = createTemplate(REPEAT_TIME, REPEAT_AMOUNT);
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        template = templateController.createEvents(template.getId(), time);

        template.setDuration(NEW_DURATION);
        template.setTitle(NEW_TITLE);

        TagTransfer tagTransfer = new TagTransfer();
        tagTransfer.setDescription(NEW_TAG);
        TagTransfer newTag = tagController.addTag(tagTransfer);
        template.getTags().add(newTag);

        template = templateController.editTemplate(template);
        template.getEvents().forEach((event) -> {
            assertEquals(NEW_TITLE, event.getTitle());
            assertEquals(NEW_DURATION, event.getDuration());
        });

        assertEquals(2, template.getTags().size());
        assertTrue(template.getEvents().stream()
                .allMatch( event -> event.getTags().stream()
                    .anyMatch(t -> t.getId().equals(tag.getId()) || t.getId().equals(newTag.getId())))
                );

        tagController.deleteTag(newTag.getId());
        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createAndDeleteOnlyEventsTest() {
        TemplateTransfer templateTransfer = createTemplate(REPEAT_TIME, REPEAT_AMOUNT);
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        template = templateController.createEvents(template.getId(), time);

        assertEquals(REPEAT_AMOUNT, template.getEvents().size());
        template = templateController.deleteEvents(template.getId());
        assertEquals(0, template.getEvents().size());

        templateController.deleteTemplate(template.getId());
    }

    private TemplateTransfer createTemplate(Long repeatTime, Integer repeatAmount) {
        TemplateTransfer templateTransfer = new TemplateTransfer();
        templateTransfer.setOwnerId(userID);
        templateTransfer.setTitle(TITLE);
        templateTransfer.setDescription(DESCRIPTION);
        templateTransfer.setDuration(DURATION);
        templateTransfer.setRepeatTime(repeatTime);
        templateTransfer.setRepeatAmount(repeatAmount);
        templateTransfer.setPrivacy(new ArrayList<>());
        templateTransfer.getPrivacy().add(privacy);
        templateTransfer.setTags(new ArrayList<>());
        templateTransfer.getTags().add(tag);
        return templateTransfer;
    }

    private void assertEvent(EventTransfer event, TemplateTransfer template) {
        assertEquals(userID, event.getOwnerId());
        assertEquals(TITLE, event.getTitle());
        assertEquals(DESCRIPTION, event.getDescription());
        assertEquals(DURATION, event.getDuration());
        assertEquals(template.getId(), event.getTemplateId());

        assertEquals(1, event.getPrivacy().size());
        assertTrue(event.getPrivacy().stream().allMatch(t -> t.getId().equals(privacy.getId())));

        assertEquals(1, event.getTags().size());
        assertTrue(event.getTags().stream().allMatch(t -> t.getId().equals(tag.getId())));

        assertNull(event.getComments());
    }

    @AfterAll
    void tearDown() {
        userController.deleteUser(userID);
        tagController.deleteTag(tag.getId());
        categoryController.deleteCategory(privacy.getId());
    }
}
