package com.curevent.services;

import com.curevent.exceptions.InvalidArgumentException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Repeat;
import com.curevent.models.entities.Template;
import com.curevent.models.forms.RepeatForm;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.repositories.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.curevent.models.enums.RepeatType.NONE;

@RequiredArgsConstructor
@Service
@Transactional
public class EventFactory {
    private static final int BASIC_COUNT = 0;
    private static final int BASIC_REPEAT_INTERVAL = 1;

    private final TemplateService templateService;
    private final TemplateRepository templateRepository;
    private final ModelMapper mapper;

    public TemplateTransfer parseRepeatForm(UUID id, RepeatForm repeatForm) {
        Template template = templateService.getEntityById(id);
        Repeat repeat = mapper.map(repeatForm, Repeat.class);
        validateRepeat(repeat);
        switch (repeat.getRepeatType()) {
            case DAILY:
                template = parseDailyRepeat(template, repeat);
                break;
            case WEEKLY:
                template = parseWeeklyRepeat(template, repeat);
                break;
            case MONTHLY:
                template = parseMonthlyRepeat(template, repeat);
                break;
            case ANNUALLY:
                template = parseAnnuallyRepeat(template, repeat);
                break;
            case NONE:
                template = parseNoneRepeat(template, repeat);
                break;
            default:
                throw new InvalidArgumentException("Invalid repeat type " + repeat.getRepeatType());
        }
        return mapper.map(template, TemplateTransfer.class);
    }

    private void validateRepeat(Repeat repeat) {
        if(repeat.getRepeatType() == null) {
            repeat.setRepeatType(NONE);
        }
        if(repeat.getRepeatInterval() == null) {
            repeat.setRepeatInterval(BASIC_REPEAT_INTERVAL);
        }
        if (repeat.getEndTime() == null && !repeat.getRepeatType().equals(NONE)) {
            throw new InvalidArgumentException("End time with repeat type must be not null");
        }
        if(repeat.getRepeatDays() == null) {
            repeat.setRepeatDays(new HashMap<>());
        }
        repeat.setDayCount(BASIC_COUNT);
        repeat.setWeekCount(BASIC_COUNT);
        repeat.setMonthCount(BASIC_COUNT);
        repeat.setYearCount(BASIC_COUNT);
    }

    private Template parseDailyRepeat(Template template, Repeat repeat) {
        repeat.setDayCount(repeat.getRepeatInterval());
        return createEvents(template, repeat);
    }

    private Template parseWeeklyRepeat(Template template, Repeat repeat) {
        repeat.setWeekCount(repeat.getRepeatInterval());
        if (repeat.getRepeatDays().isEmpty()) {
            return createEvents(template, repeat);
        }

        LocalDate startDate = repeat.getStartTime().toLocalDateTime().toLocalDate();
        repeat.getRepeatDays().forEach( (dayOfWeek, time) -> {
            LocalDate nextWeekDayDate = startDate.getDayOfWeek() == dayOfWeek ? startDate : startDate.with(TemporalAdjusters.next(dayOfWeek));
            LocalTime weekDayTime = time.toLocalTime();
            LocalDateTime nextStartTime = nextWeekDayDate.atTime(weekDayTime);
            repeat.setStartTime(Timestamp.valueOf(nextStartTime));
            createEvents(template, repeat);
        });
        return template;
    }

    private Template parseMonthlyRepeat(Template template, Repeat repeat) {
        repeat.setMonthCount(repeat.getRepeatInterval());
        return createEvents(template, repeat);
    }

    private Template parseAnnuallyRepeat(Template template, Repeat repeat) {
        repeat.setYearCount(repeat.getRepeatInterval());
        return createEvents(template, repeat);
    }

    private Template parseNoneRepeat(Template template, Repeat repeat) {
        template.getEvents().add(createEvent(template, repeat.getStartTime()));
        return templateRepository.save(template);
    }

    private Event createEvent (Template template, Timestamp time) {
        Event event = new Event();
        event.setTime(time);
        templateService.fillEvent(event, template);
        return event;
    }

    private Template createEvents(Template template, Repeat repeat) {
        LocalDateTime startTime = repeat.getStartTime().toLocalDateTime();
        LocalDateTime endTime = repeat.getEndTime().toLocalDateTime();
        template.getEvents().addAll(Stream.iterate(
                startTime,
                time -> time.isBefore(endTime),
                time -> time.plusYears(repeat.getYearCount())
                        .plusMonths(repeat.getMonthCount())
                        .plusWeeks(repeat.getWeekCount())
                        .plusDays(repeat.getDayCount()))
                .map(Timestamp::valueOf)
                .map(time -> createEvent(template, time))
                .collect(Collectors.toList()));
        return templateRepository.save(template);
    }
}
