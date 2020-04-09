package com.curevent.services;

import com.curevent.exceptions.InvalidArgumentException;
import com.curevent.models.entities.Event;
import com.curevent.models.entities.Repetition;
import com.curevent.models.entities.Template;
import com.curevent.models.forms.RepetitionForm;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.repositories.TemplateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
@Transactional
public class EventFactoryService {
    @Autowired
    private final TemplateService templateService;
    @Autowired
    private final TemplateRepository templateRepository;
    @Autowired
    private final ModelMapper mapper;

    public TemplateTransfer parseRepetitionForm(UUID id, RepetitionForm repetitionForm) {
        Template template = templateService.getEntityById(id);
        Repetition repetition = mapper.map(repetitionForm, Repetition.class);
        validateRepetition(repetition);
        switch (repetition.getRepetitionType()) {
            case "day":
                template = parseDailyRepetition(template, repetition);
                break;
            case "week":
                template = parseWeeklyRepetition(template, repetition);
                break;
            case "month":
                template = parseMonthlyRepetition(template, repetition);
                break;
            case "year":
                template = parseAnnuallyRepetition(template, repetition);
                break;
            case "no":
                template = parseNoRepetition(template, repetition);
                break;
        }
        return mapper.map(template, TemplateTransfer.class);
    }

    private void validateRepetition(Repetition repetition) {
        if(repetition.getRepetitionType() == null) {
            repetition.setRepetitionType("no");
        }
        if(repetition.getRepetitionInterval() == null) {
            repetition.setRepetitionInterval(1);
        }
        if (repetition.getEndTime() == null && !repetition.getRepetitionType().equals("no")) {
            throw new InvalidArgumentException("EndTime with repetitionType must be not null");
        }
        repetition.setDayCount(0);
        repetition.setWeekCount(0);
        repetition.setMonthCount(0);
        repetition.setYearCount(0);
    }

    private Template parseDailyRepetition(Template template, Repetition repetition) {
        repetition.setDayCount(repetition.getRepetitionInterval());
        return createEvents(template, repetition);
    }

    private Template parseWeeklyRepetition(Template template, Repetition repetition) {
        repetition.setWeekCount(repetition.getRepetitionInterval());
        if (repetition.getRepetitionDays() == null) {
            return createEvents(template, repetition);
        }

        LocalDate startDate = repetition.getStartTime().toLocalDateTime().toLocalDate();
        repetition.getRepetitionDays().forEach( (dayOfWeek, time) -> {
            LocalDate nextWeekDayDate = startDate.with(TemporalAdjusters.next(dayOfWeek));
            LocalTime weekDayTime = time.toLocalTime();
            LocalDateTime nextStartTime = nextWeekDayDate.atTime(weekDayTime);
            repetition.setStartTime(Timestamp.valueOf(nextStartTime));
            createEvents(template, repetition);
        });
        return template;
    }

    private Template parseMonthlyRepetition(Template template, Repetition repetition) {
        repetition.setMonthCount(repetition.getRepetitionInterval());
        return createEvents(template, repetition);
    }

    private Template parseAnnuallyRepetition(Template template, Repetition repetition) {
        repetition.setYearCount(repetition.getRepetitionInterval());
        return createEvents(template, repetition);
    }

    private Template parseNoRepetition(Template template, Repetition repetition) {
        template.getEvents().add(createEvent(template, repetition.getStartTime()));
        return templateRepository.save(template);
    }

    private Event createEvent (Template template, Timestamp time) {
        Event event = new Event();
        event.setTime(time);
        templateService.fillEvent(event, template);
        return event;
    }

    private Template createEvents(Template template, Repetition repetition) {
        LocalDateTime startTime = repetition.getStartTime().toLocalDateTime();
        LocalDateTime endTime = repetition.getEndTime().toLocalDateTime();
        template.getEvents().addAll(Stream.iterate(
                startTime,
                time -> time.isBefore(endTime),
                time -> time.plusYears(repetition.getYearCount())
                        .plusMonths(repetition.getMonthCount())
                        .plusWeeks(repetition.getWeekCount())
                        .plusDays(repetition.getDayCount()))
                .map(Timestamp::valueOf)
                .map(time -> createEvent(template, time))
                .collect(Collectors.toList()));
        return templateRepository.save(template);
    }


}
