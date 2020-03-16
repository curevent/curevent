package com.curevent.utils.mapping;

import com.curevent.models.entities.CategoryEntity;
import com.curevent.models.transfers.CategoryTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryMapper {

    @Autowired
    private ModelMapper mapper;

    public CategoryEntity toEntity(CategoryTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, CategoryEntity.class);
    }

    public CategoryTransfer toTransfer(CategoryEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, CategoryTransfer.class);
    }
}
