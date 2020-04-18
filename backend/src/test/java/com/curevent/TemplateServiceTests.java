package com.curevent;

import com.curevent.models.entities.Category;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Template;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.repositories.TemplateRepository;
import com.curevent.services.TemplateService;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TemplateServiceTests {
    public static final UUID TEMPLATE_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a57");
    public static final UUID USER_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a58");
    public static final UUID FIRST_EVENT_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a60");
    public static final UUID SECOND_EVENT_ID = UUID.fromString("d5338977-923a-4913-92a6-243246084a61");
    public static final String TITLE = "title";
    public static final String NEW_TITLE = "new_title";
    public static final String DESCRIPTION = "description";
    public static final long DURATION = 60L;
    public static final long NEW_DURATION = 1000;
    public static final String CATEGORY = "category";
    public static final long CATEGORY_ID = 1L;
    public static final Timestamp FIRST_EVENT_TIME = Timestamp.valueOf("2020-03-20 17:00:00.000");
    public static final Timestamp SECOND_EVENT_TIME = Timestamp.valueOf("2020-03-21 17:00:00.000");
    public static final int EVENTS_COUNT = 0;

    private Template template;
    private Category category;

    @MockBean
    private TemplateRepository templateRepository;

    @Resource
    private TemplateService templateService;

    @BeforeAll
    public void setUp(){
        category = new Category(CATEGORY_ID, CATEGORY);

        template = new Template();
        template.setOwnerId(USER_ID);
        template.setTitle(TITLE);
        template.setDescription(DESCRIPTION);
        template.setDuration(DURATION);
        template.setPrivacy(List.of(category));
        template.setTags(new ArrayList<>());
        template.setEvents(new ArrayList<>());
        template.getEvents().add(createEvent(FIRST_EVENT_TIME, FIRST_EVENT_ID));
        template.getEvents().add(createEvent(SECOND_EVENT_TIME, SECOND_EVENT_ID));
    }

    @Test
    public void edit_template_and_get_template_events_with_new_title_and_duration_test() {
        when(templateRepository.findById(TEMPLATE_ID)).thenReturn(Optional.of(template));
        when(templateRepository.save(Mockito.any(Template.class))).thenAnswer(i -> i.getArguments()[0]);

        TemplateTransfer template = templateService.getOneById(TEMPLATE_ID);
        template.setDuration(NEW_DURATION);
        template.setTitle(NEW_TITLE);

        template = templateService.updateEvents(template);
        template.getEvents().forEach((event) -> {
            assertEquals(NEW_TITLE, event.getTitle());
            assertEquals(NEW_DURATION, event.getDuration());
            assertEquals(DESCRIPTION, event.getDescription());
            assertEquals(USER_ID, event.getOwnerId());
        });
    }

    @Test
    public void delete_only_template_events_test() {
        when(templateRepository.findById(TEMPLATE_ID)).thenReturn(Optional.of(template));
        when(templateRepository.save(Mockito.any(Template.class))).thenAnswer(i -> i.getArguments()[0]);

        TemplateTransfer template = templateService.deleteEvents(TEMPLATE_ID);
        assertEquals(EVENTS_COUNT, template.getEvents().size());
    }

    private Event createEvent(Timestamp time, UUID id) {
        Event event = new Event();
        event.setTime(time);
        event.setId(id);
        event.setOwnerId(USER_ID);
        event.setTitle(TITLE);
        event.setDescription(DESCRIPTION);
        event.setPrivacy(List.of(category));
        event.setTags(new ArrayList<>());
        return event;
    }
}
