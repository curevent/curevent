package com.curevent.services;

import com.curevent.exceptions.InvalidArgumentException;
import com.curevent.models.entities.Repeat;
import com.curevent.models.entities.Template;
import com.curevent.models.forms.RepeatForm;
import com.curevent.models.transfers.TemplateTransfer;
import com.curevent.services.registry.RepeatServiceRegistry;
import com.curevent.services.repeatServices.RepeatService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

import static com.curevent.models.enums.RepeatType.NONE;

@RequiredArgsConstructor
@Service
@Transactional
public class EventFactory {
    private static final int BASIC_COUNT = 0;
    private static final int BASIC_REPEAT_INTERVAL = 1;

    private final RepeatServiceRegistry registry;
    private final TemplateService templateService;
    private final ModelMapper mapper;

    public TemplateTransfer parseRepeatForm(UUID id, RepeatForm repeatForm) {
        Template template = templateService.getEntityById(id);
        Repeat repeat = mapper.map(repeatForm, Repeat.class);
        validateRepeat(repeat);
        RepeatService repeatService = registry.getByType(repeat.getRepeatType());
        return mapper.map(repeatService.parseRepeatForm(template, repeat), TemplateTransfer.class);
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
}
