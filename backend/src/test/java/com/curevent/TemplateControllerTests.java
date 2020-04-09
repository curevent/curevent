package com.curevent;

import com.curevent.controllers.*;
import com.curevent.exceptions.InvalidArgumentException;
import com.curevent.models.forms.RegisterForm;
import com.curevent.models.forms.RepetitionForm;
import com.curevent.models.transfers.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public static final int REPETITION_INTERVAL = 3;
    public static final int EVENTS_AMOUNT = 2;

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

        time = Timestamp.valueOf("2020-03-20 17:00:00.000");
    }

    @Test
    void createDailyEventsTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusDays(REPETITION_INTERVAL + 1));
        RepetitionForm repetition = createRepetition("day", REPETITION_INTERVAL, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repetition).getEvents();
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
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusWeeks(REPETITION_INTERVAL + 1));
        RepetitionForm repetition = createRepetition("week", REPETITION_INTERVAL, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repetition).getEvents();
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
        RepetitionForm repetition = createRepetition("week", 1, endTime);
        repetition.setRepetitionDays(new HashMap<>());
        repetition.getRepetitionDays().put(DayOfWeek.MONDAY, Time.valueOf(("17:00:00")));
        repetition.getRepetitionDays().put(DayOfWeek.WEDNESDAY, Time.valueOf(("19:00:00")));

        List<EventTransfer> events = templateController.createEvents(template.getId(), repetition).getEvents();
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
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusMonths(REPETITION_INTERVAL + 1));
        RepetitionForm repetition = createRepetition("month", REPETITION_INTERVAL, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repetition).getEvents();
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
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusYears(REPETITION_INTERVAL + 1));
        RepetitionForm repetition = createRepetition("year", REPETITION_INTERVAL, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repetition).getEvents();
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
        RepetitionForm repetition = createRepetition(null, REPETITION_INTERVAL, null);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repetition).getEvents();
        assertEquals(1, events.size());
        events.forEach(event -> assertEvent(event, template));
        assertTrue(events.stream().allMatch(e -> e.getTime().equals(time)));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createEventsWithoutEndTimeTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        RepetitionForm repetition = createRepetition("day", REPETITION_INTERVAL, null);

        assertThrows(InvalidArgumentException.class, () -> templateController.createEvents(template.getId(), repetition));

        templateController.deleteTemplate(template.getId());
    }

    @Test
    void createEventsWithoutRepetitionIntervalTest() {
        TemplateTransfer templateTransfer = createTemplate();
        TemplateTransfer template = templateController.addTemplate(templateTransfer);
        Timestamp endTime = Timestamp.valueOf(time.toLocalDateTime().plusDays(EVENTS_AMOUNT));
        RepetitionForm repetition = createRepetition("day", null, endTime);

        List<EventTransfer> events = templateController.createEvents(template.getId(), repetition).getEvents();
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
        RepetitionForm repetition = createRepetition("day", REPETITION_INTERVAL, endTime);
        template = templateController.createEvents(template.getId(), repetition);

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
        RepetitionForm repetition = createRepetition("day", REPETITION_INTERVAL, endTime);
        template = templateController.createEvents(template.getId(), repetition);

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

    private RepetitionForm createRepetition(String repetitionType, Integer repetitionInterval, Timestamp endTime) {
        RepetitionForm repetition = new RepetitionForm();
        repetition.setRepetitionType(repetitionType);
        repetition.setRepetitionInterval(repetitionInterval);
        repetition.setStartTime(time);
        repetition.setEndTime(endTime);
        return repetition;
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
