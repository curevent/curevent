package com.curevent;

import com.curevent.exceptions.InvalidArgumentException;
import com.curevent.models.entities.Category;
import com.curevent.models.entities.Template;
import com.curevent.models.enums.RepeatType;
import com.curevent.models.forms.RepeatForm;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.repositories.TemplateRepository;
import com.curevent.services.EventFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventFactoryTests {
    public static final UUID TEMPLATE_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a57");
    public static final UUID USER_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a58");
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final long DURATION = 60L;
    public static final String CATEGORY = "category";
    public static final long CATEGORY_ID = 1L;
    public static final int REPEAT_INTERVAL = 3;
    public static final int REPEAT_END_TIME_INTERVAL = 4;
    public static final Time MONDAY_TIME = Time.valueOf(("17:00:00"));
    public static final Time WEDNESDAY_TIME = Time.valueOf(("19:00:00"));
    public static final Timestamp TIME = Timestamp.valueOf("2020-03-20 17:00:00.000");
    public static final Timestamp FIRST_EVENT_TIME = Timestamp.valueOf("2020-03-20 17:00:00.000");
    public static final Timestamp DAILY_SECOND_EVENT_TIME = Timestamp.valueOf("2020-03-23 17:00:00.000");
    public static final Timestamp WEEKLY_SECOND_EVENT_TIME = Timestamp.valueOf("2020-04-10 17:00:00.000");
    public static final Timestamp MONTHLY_SECOND_EVENT_TIME = Timestamp.valueOf("2020-06-20 17:00:00.000");
    public static final Timestamp ANNUALLY_SECOND_EVENT_TIME = Timestamp.valueOf("2023-03-20 17:00:00.000");
    public static final Timestamp EVERY_DAY_SECOND_EVENT_TIME = Timestamp.valueOf("2020-03-21 17:00:00.000");
    public static final Timestamp EVERY_DAY_THIRD_EVENT_TIME = Timestamp.valueOf("2020-03-22 17:00:00.000");
    public static final Timestamp EVERY_DAY_FOURTH_EVENT_TIME = Timestamp.valueOf("2020-03-23 17:00:00.000");
    public static final Timestamp COMPLEX_LOGIC_FIRST_EVENT_TIME = Timestamp.valueOf("2020-03-23 17:00:00.000");
    public static final Timestamp COMPLEX_LOGIC_SECOND_EVENT_TIME = Timestamp.valueOf("2020-03-25 19:00:00.000");
    public static final Timestamp COMPLEX_LOGIC_THIRD_EVENT_TIME = Timestamp.valueOf("2020-04-13 17:00:00.000");
    public static final Timestamp COMPLEX_LOGIC_FOURTH_EVENT_TIME = Timestamp.valueOf("2020-04-15 19:00:00.000");
    public static final int EVENTS_AMOUNT = 2;
    public static final int ONE_EVENT_AMOUNT = 1;
    public static final int COMPLEX_LOGIC_EVENTS_AMOUNT = 4;

    private Template template;

    @MockBean
    private TemplateRepository templateRepository;

    @Resource
    private EventFactory eventFactory;

    @BeforeAll
    public void setUp(){
        Category category = new Category(CATEGORY_ID, CATEGORY, USER_ID);

        template = new Template();
        template.setId(TEMPLATE_ID);
        template.setOwnerId(USER_ID);
        template.setTitle(TITLE);
        template.setDescription(DESCRIPTION);
        template.setDuration(DURATION);
        template.setPrivacy(List.of(category));
        template.setTags(new ArrayList<>());
    }

    @BeforeEach
    public void setUpMock() {
        template.setEvents(new ArrayList<>());
        when(templateRepository.findById(TEMPLATE_ID)).thenReturn(Optional.of(template));
        when(templateRepository.save(Mockito.any(Template.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void create_daily_events_return_two_events_test() {
        RepeatForm repeat = createRepeat(RepeatType.DAILY, REPEAT_INTERVAL);
        List<EventTransfer> events = eventFactory.parseRepeatForm(TEMPLATE_ID, repeat).getEvents();
        assertEvents(events, EVENTS_AMOUNT);
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(DAILY_SECOND_EVENT_TIME)));
    }

    @Test
    void create_monthly_events_return_two_events_test() {
        RepeatForm repeat = createRepeat(RepeatType.MONTHLY, REPEAT_INTERVAL);
        List<EventTransfer> events = eventFactory.parseRepeatForm(TEMPLATE_ID, repeat).getEvents();
        assertEvents(events, EVENTS_AMOUNT);
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(MONTHLY_SECOND_EVENT_TIME)));
    }

    @Test
    void create_annually_events_return_two_events_test() {
        RepeatForm repeat = createRepeat(RepeatType.ANNUALLY, REPEAT_INTERVAL);
        List<EventTransfer> events = eventFactory.parseRepeatForm(TEMPLATE_ID, repeat).getEvents();
        assertEvents(events, EVENTS_AMOUNT);
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(ANNUALLY_SECOND_EVENT_TIME)));
    }

    @Test
    void create_weekly_events_return_two_events_test() {
        RepeatForm repeat = createRepeat(RepeatType.WEEKLY, REPEAT_INTERVAL);
        List<EventTransfer> events = eventFactory.parseRepeatForm(TEMPLATE_ID, repeat).getEvents();
        assertEvents(events, EVENTS_AMOUNT);
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(WEEKLY_SECOND_EVENT_TIME)));
    }

    @Test
    void create_weekly_events_with_complex_repeat_logi_return_monday_and_wednesday_events_test() {
        RepeatForm repeat = createRepeat(RepeatType.WEEKLY, REPEAT_INTERVAL);
        repeat.setRepeatDays(new HashMap<>());
        repeat.getRepeatDays().put(DayOfWeek.MONDAY, MONDAY_TIME);
        repeat.getRepeatDays().put(DayOfWeek.WEDNESDAY, WEDNESDAY_TIME);
        List<EventTransfer> events = eventFactory.parseRepeatForm(TEMPLATE_ID, repeat).getEvents();
        assertEquals(COMPLEX_LOGIC_EVENTS_AMOUNT, events.size());
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(COMPLEX_LOGIC_FIRST_EVENT_TIME)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(COMPLEX_LOGIC_SECOND_EVENT_TIME)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(COMPLEX_LOGIC_THIRD_EVENT_TIME)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(COMPLEX_LOGIC_FOURTH_EVENT_TIME)));
    }

    @Test
    void create_none_repeat_events_return_only_one_event_test() {
        RepeatForm repeat = createRepeat(RepeatType.NONE, null);
        List<EventTransfer> events = eventFactory.parseRepeatForm(TEMPLATE_ID, repeat).getEvents();
        assertEvents(events, ONE_EVENT_AMOUNT);
    }

    @Test
    void create_daily_events_with_null_repeat_interval_return_four_events_test() {
        RepeatForm repeat = createRepeat(RepeatType.DAILY, null);
        List<EventTransfer> events = eventFactory.parseRepeatForm(TEMPLATE_ID, repeat).getEvents();
        assertEvents(events, COMPLEX_LOGIC_EVENTS_AMOUNT);
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(EVERY_DAY_SECOND_EVENT_TIME)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(EVERY_DAY_THIRD_EVENT_TIME)));
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(EVERY_DAY_FOURTH_EVENT_TIME)));
    }

    @Test
    void create_events_with_null_end_time_throw_exception_test() {
        RepeatForm repeat = createRepeat(RepeatType.WEEKLY, null);
        repeat.setEndTime(null);
        assertThrows(InvalidArgumentException.class, () -> eventFactory.parseRepeatForm(TEMPLATE_ID, repeat));
    }

    private RepeatForm createRepeat(RepeatType repeatType, Integer repeatInterval) {
        Timestamp endTime = null;
        switch (repeatType){
            case DAILY:
                endTime = Timestamp.valueOf(TIME.toLocalDateTime().plusDays(REPEAT_END_TIME_INTERVAL));
                break;
            case WEEKLY:
                endTime = Timestamp.valueOf(TIME.toLocalDateTime().plusWeeks(REPEAT_END_TIME_INTERVAL));
                break;
            case MONTHLY:
                endTime = Timestamp.valueOf(TIME.toLocalDateTime().plusMonths(REPEAT_END_TIME_INTERVAL));
                break;
            case ANNUALLY:
                endTime = Timestamp.valueOf(TIME.toLocalDateTime().plusYears(REPEAT_END_TIME_INTERVAL));
                break;
        }

        RepeatForm repeat = new RepeatForm();
        repeat.setRepeatType(repeatType);
        repeat.setRepeatInterval(repeatInterval);
        repeat.setStartTime(TIME);
        repeat.setEndTime(endTime);
        return repeat;
    }

    private void assertEvent(EventTransfer event) {
        assertEquals(USER_ID, event.getOwnerId());
        assertEquals(TITLE, event.getTitle());
        assertEquals(DESCRIPTION, event.getDescription());
        assertEquals(DURATION, event.getDuration());
        assertEquals(TEMPLATE_ID, event.getTemplateId());

        assertEquals(1, event.getPrivacy().size());
        assertTrue(event.getPrivacy().stream().allMatch(t -> t.getId().equals(CATEGORY_ID)));

        assertNull(event.getComments());
    }

    private void assertEvents(List<EventTransfer> events, int eventAmount) {
        assertEquals(eventAmount, events.size());
        events.forEach(this::assertEvent);
        assertTrue(events.stream().anyMatch(e -> e.getTime().equals(FIRST_EVENT_TIME)));
    }
}
