package com.curevent.utils.mapping;

import com.curevent.models.entities.TemplateTagEntity;
import com.curevent.models.transfers.TemplateTagTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TemplateTagMapper {

    @Autowired
    private ModelMapper mapper;

    public TemplateTagEntity toEntity(TemplateTagTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, TemplateTagEntity.class);
    }

    public TemplateTagTransfer toTransfer(TemplateTagEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, TemplateTagTransfer.class);
    }
}
