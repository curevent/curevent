package com.curevent.services.repeatServices;

import com.curevent.annotations.RepeatServiceFor;
import com.curevent.models.entities.Repeat;
import com.curevent.models.entities.Template;
import com.curevent.models.enums.RepeatType;
import com.curevent.repositories.TemplateRepository;
import com.curevent.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Service
@RepeatServiceFor(type = RepeatType.WEEKLY)
public class WeeklyRepeatService extends RepeatService {
    @Autowired
    public WeeklyRepeatService(TemplateService templateService, TemplateRepository templateRepository) {
        super(templateService, templateRepository);
    }

    @Override
    public Template parseRepeatForm(Template template, Repeat repeat) {
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
}
