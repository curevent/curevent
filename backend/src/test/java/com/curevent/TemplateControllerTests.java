package com.curevent;

import com.curevent.controllers.*;
import com.curevent.exceptions.InvalidArgumentException;
import com.curevent.models.forms.RegisterForm;
import com.curevent.models.forms.RepeatForm;
import com.curevent.models.transfers.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.HashMap;
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
    public static final String NEW_TITLE = "new_title";
    public static final long NEW_DURATION = 1000;
    public static final String NEW_TAG = "new_tag";
    public static final int REPEAT_INTERVAL = 3;
    public static final int EVENTS_AMOUNT = 2;

    @Resource
    private UserController userController;
    @Resource
    private TemplateController templateController;
    @Resource
    private AuthController authController;
    @Resource
    private CategoryController categoryController;
    @Resource
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

        time = Timestamp.valueOf("2020-03-20 17:00:00.000");
    }

    @Test
    void createDailyEventsTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusDays(REPEAT_INTERVAL + 1));
        RepeatForm repeat = createRepeat("day", REPEAT_INTERVAL, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repeat).getEvents();
        assertEquals(EVENTS_AMOUNT, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(time)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-03-23 17:00:00.000"))));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createWeeklyEventsTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusWeeks(REPEAT_INTERVAL + 1));
        RepeatForm repeat = createRepeat("week", REPEAT_INTERVAL, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repeat).getEvents();
        assertEquals(EVENTS_AMOUNT, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(time)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-04-10 17:00:00.000"))));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createComplexWeeklyEventsTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusWeeks(2));
        RepeatForm repeat = createRepeat("week", 1, endTime);
        repeat.setRepeatDays(new HashMap<>());
        repeat.getRepeatDays().put(DayOfWeek.MONDAY, Time.valueOf(("17:00:00")));
        repeat.getRepeatDays().put(DayOfWeek.WEDNESDAY, Time.valueOf(("19:00:00")));

        List<EventTransfer> events = templateController.createEvents(template.getId(), repeat).getEvents();
        assertEquals(4, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-03-23 17:00:00.000"))));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-03-25 19:00:00.000"))));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-03-30 17:00:00.000"))));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-04-01 19:00:00.000"))));
        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createMonthlyEventsTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusMonths(REPEAT_INTERVAL + 1));
        RepeatForm repeat = createRepeat("month", REPEAT_INTERVAL, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repeat).getEvents();
        assertEquals(EVENTS_AMOUNT, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(time)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-06-20 17:00:00.000"))));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createAnnuallyEventsTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusYears(REPEAT_INTERVAL + 1));
        RepeatForm repeat = createRepeat("year", REPEAT_INTERVAL, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repeat).getEvents();
        assertEquals(EVENTS_AMOUNT, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(time)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2023-03-20 17:00:00.000"))));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createOneEventTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        RepeatForm repeat = createRepeat(null, REPEAT_INTERVAL, null);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repeat).getEvents();
        assertEquals(1, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().allMatch(e -> e.getTime().equals(time)));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createEventsWithoutEndTimeTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        RepeatForm repeat = createRepeat("day", REPEAT_INTERVAL, null);

        assertThrows(InvalidArgumentException.class, () -> templateController.createEvents(template.getId(), repeat));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createEventsWithInvalidRepeatType() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        RepeatForm repeat = createRepeat("type", REPEAT_INTERVAL, null);

        assertThrows(InvalidArgumentException.class, () -> templateController.createEvents(template.getId(), repeat));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createEventsWithoutRepeatIntervalTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusDays(EVENTS_AMOUNT));
        RepeatForm repeat = createRepeat("day", null, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repeat).getEvents();
        assertEquals(EVENTS_AMOUNT, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(time)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(Timestamp.valueOf("2020-03-21 17:00:00.000"))));
        templateController.deleteTemplate(template.getId());
    }

    @Test
    void editTemplateWithEventsTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf("2020-03-24 17:00:00.000");
        RepeatForm repeat = createRepeat("day", REPEAT_INTERVAL, endTime);
        template = templateController.createEvents(template.getId(), repeat);

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

        assertEquals(EVENTS_AMOUNT, template.getTags().size());
        assertTrue(template.getEvents().stream()
                .allMatch( event -> event.getTags().stream()
                    .anyMatch(t -> t.getId().equals(tag.getId()) || t.getId().equals(newTag.getId())))
                );

        tagController.deleteTag(newTag.getId());
        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createAndDeleteOnlyEventsTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf("2020-03-24 17:00:00.000");
        RepeatForm repeat = createRepeat("day", REPEAT_INTERVAL, endTime);
        template = templateController.createEvents(template.getId(), repeat);

        assertEquals(EVENTS_AMOUNT, template.getEvents().size());
        template = templateController.deleteEvents(template.getId());
        assertEquals(0, template.getEvents().size());

        templateController.deleteTemplate(template.getId());
    }

    private TemplateTransfer createTemplate() {
        TemplateTransfer templateTransfer = new TemplateTransfer();
        templateTransfer.setOwnerId(userID);
        templateTransfer.setTitle(TITLE);
        templateTransfer.setDescription(DESCRIPTION);
        templateTransfer.setDuration(DURATION);
        templateTransfer.setPrivacy(List.of(privacy));
        templateTransfer.setTags(List.of(tag));
        return templateTransfer;
    }

    private RepeatForm createRepeat(String repeatType, Integer repeatInterval, Timestamp endTime) {
        RepeatForm repeat = new RepeatForm();
        repeat.setRepeatType(repeatType);
        repeat.setRepeatInterval(repeatInterval);
        repeat.setStartTime(time);
        repeat.setEndTime(endTime);
        return repeat;
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
