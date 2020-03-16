package com.curevent.utils.mapping;

import com.curevent.models.entities.CategoryEntity;
import com.curevent.models.entities.EventEntity;
import com.curevent.models.transfers.CategoryTransfer;
import com.curevent.models.transfers.EventTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EventMapper {

    @Autowired
    private ModelMapper mapper;

    public EventEntity toEntity(EventTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, EventEntity.class);
    }

    public EventTransfer toTransfer(EventEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, EventTransfer.class);
    }
}
