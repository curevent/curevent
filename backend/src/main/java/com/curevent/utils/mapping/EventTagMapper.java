package com.curevent.utils.mapping;

import com.curevent.models.entities.EventEntity;
import com.curevent.models.entities.EventTagEntity;
import com.curevent.models.transfers.EventTagTransfer;
import com.curevent.models.transfers.EventTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EventTagMapper {

    @Autowired
    private ModelMapper mapper;

    public EventTagEntity toEntity(EventTagTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, EventTagEntity.class);
    }

    public EventTagTransfer toTransfer(EventTagEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, EventTagTransfer.class);
    }
}
