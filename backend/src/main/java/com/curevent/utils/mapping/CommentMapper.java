package com.curevent.utils.mapping;

import com.curevent.models.entities.CommentEntity;
import com.curevent.models.transfers.CommentTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommentMapper {

    @Autowired
    private ModelMapper mapper;

    public CommentEntity toEntity(CommentTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, CommentEntity.class);
    }

    public CommentTransfer toTransfer(CommentEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, CommentTransfer.class);
    }
}
