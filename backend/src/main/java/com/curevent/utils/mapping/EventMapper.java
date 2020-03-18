package com.curevent.utils.mapping;

import com.curevent.models.entities.Event;
import com.curevent.models.transfers.EventTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EventMapper {

    @Autowired
    private ModelMapper mapper;

    public Event toEntity(EventTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, Event.class);
    }

    public EventTransfer toTransfer(Event entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, EventTransfer.class);
    }
}
