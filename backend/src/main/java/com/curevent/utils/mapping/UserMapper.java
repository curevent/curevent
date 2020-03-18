package com.curevent.utils.mapping;

import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.UserTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    private final ModelMapper mapper;

    @Autowired
    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UserEntity toEntity(UserTransfer transfer) {
        return Objects.isNull(transfer) ? null : mapper.map(transfer, UserEntity.class);
    }

    public UserTransfer toTransfer(UserEntity entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, UserTransfer.class);
    }
}
