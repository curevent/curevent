package com.curevent.controllers;

import com.curevent.exceptions.CategoryNotFoundException;
import com.curevent.models.entities.CategoryEntity;
import com.curevent.models.transfers.CategoryTransfer;
import com.curevent.services.CategoryService;
import com.curevent.utils.mapping.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public CategoryTransfer getCategory(@PathVariable Long id) {
        CategoryEntity categoryEntity = categoryService.getOneById(id);
        if (categoryEntity == null) {
            throw new CategoryNotFoundException();
        }
        return mapper.toTransfer(categoryEntity);
    }

    @PostMapping("/add")
    public void addCategory(@RequestBody @Valid CategoryTransfer categoryTransfer) {
        CategoryEntity categoryEntity = mapper.toEntity(categoryTransfer);
        categoryService.add(categoryEntity);
    }
}
