package com.curevent.controllers;

import com.curevent.exceptions.CategoryNotFoundException;
import com.curevent.exceptions.UserAlreadyExistsException;
import com.curevent.exceptions.UserNotFoundException;
import com.curevent.models.entities.CategoryEntity;
import com.curevent.models.entities.UserEntity;
import com.curevent.models.transfers.CategoryTransfer;
import com.curevent.models.transfers.UserTransfer;
import com.curevent.services.CategoryService;
import com.curevent.utils.mapping.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public CategoryTransfer getUser(@PathVariable Long id) {
        CategoryEntity categoryEntity = categoryService.getOneById(id);
        if (categoryEntity == null) {
            throw new CategoryNotFoundException();
        }
        return mapper.toTransfer(categoryEntity);
    }

    @PostMapping("/add")
    public void addUser(@RequestBody @Valid CategoryTransfer categoryTransfer) {
        CategoryEntity categoryEntity = mapper.toEntity(categoryTransfer);
        categoryService.add(categoryEntity);
    }
}
