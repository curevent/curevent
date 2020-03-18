package com.curevent.utils.mapping;

import com.curevent.models.entities.Comment;
import com.curevent.models.transfers.CommentTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommentMapper {

    @Autowired
    private ModelMapper mapper;

    public Comment toEntity(CommentTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, Comment.class);
    }

    public CommentTransfer toTransfer(Comment entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, CommentTransfer.class);
    }
}
