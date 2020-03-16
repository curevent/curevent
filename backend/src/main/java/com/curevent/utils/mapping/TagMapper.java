package com.curevent.utils.mapping;

import com.curevent.models.entities.EventTagEntity;
import com.curevent.models.entities.TagEntity;
import com.curevent.models.transfers.EventTagTransfer;
import com.curevent.models.transfers.TagTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TagMapper {

    @Autowired
    private ModelMapper mapper;

    public TagEntity toEntity(TagTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, TagEntity.class);
    }

    public TagTransfer toTransfer(TagEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, TagTransfer.class);
    }
}
