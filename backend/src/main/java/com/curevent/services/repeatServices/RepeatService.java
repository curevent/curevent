package com.curevent.services.repeatServices;


import com.curevent.models.entities.Event;
import com.curevent.models.entities.Repeat;
import com.curevent.models.entities.Template;
import com.curevent.repositories.TemplateRepository;
import com.curevent.services.TemplateService;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public abstract class RepeatService {
    protected final TemplateService templateService;
    protected final TemplateRepository templateRepository;

    public abstract Template parseRepeatForm(Template template, Repeat repeat);

    protected Event createEvent (Template template, Timestamp time) {
        Event event = new Event();
        event.setTime(time);
        templateService.fillEvent(event, template);
        return event;
    }

    protected Template createEvents(Template template, Repeat repeat) {
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
