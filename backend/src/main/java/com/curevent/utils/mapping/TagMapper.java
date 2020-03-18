package com.curevent.utils.mapping;

import com.curevent.models.entities.Tag;
import com.curevent.models.transfers.TagTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TagMapper {

    @Autowired
    private ModelMapper mapper;

    public Tag toEntity(TagTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, Tag.class);
    }

    public TagTransfer toTransfer(Tag entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, TagTransfer.class);
    }
}
