package com.curevent.utils.mapping;

import com.curevent.models.entities.RelationshipEntity;
import com.curevent.models.transfers.RelationshipTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RelationshipMapper {

    @Autowired
    private ModelMapper mapper;

    public RelationshipEntity toEntity(RelationshipTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, RelationshipEntity.class);
    }

    public RelationshipTransfer toTransfer(RelationshipEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, RelationshipTransfer.class);
    }
}
