package com.curevent.utils.mapping;

import com.curevent.models.entities.Category;
import com.curevent.models.transfers.CategoryTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryMapper {

    @Autowired
    private ModelMapper mapper;

    public Category toEntity(CategoryTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, Category.class);
    }

    public CategoryTransfer toTransfer(Category entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, CategoryTransfer.class);
    }
}
