package com.curevent.utils.mapping;

import com.curevent.models.entities.Template;
import com.curevent.models.transfers.TemplateTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TemplateMapper {

    @Autowired
    private ModelMapper mapper;

    public Template toEntity(TemplateTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, Template.class);
    }

    public TemplateTransfer toTransfer(Template entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, TemplateTransfer.class);
    }
}
