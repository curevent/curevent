package com.curevent.utils;

import com.curevent.models.entities.UserEntity;
import com.curevent.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserEntity user = (UserEntity) o;
        if (userService.getOneByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "");
        }
        if (userService.getOneByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "");
        }
    }
}
