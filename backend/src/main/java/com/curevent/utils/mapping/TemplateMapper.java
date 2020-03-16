package com.curevent.utils.mapping;

import com.curevent.models.entities.EventEntity;
import com.curevent.models.entities.TemplateEntity;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.models.transfers.TemplateTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TemplateMapper {

    @Autowired
    private ModelMapper mapper;

    public TemplateEntity toEntity(TemplateTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, TemplateEntity.class);
    }

    public TemplateTransfer toTransfer(TemplateEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, TemplateTransfer.class);
    }
}
