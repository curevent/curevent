package com.curevent.controllers;

import com.curevent.exceptions.CategoryNotFoundException;
import com.curevent.models.entities.Category;
import com.curevent.models.entities.Event;
import com.curevent.models.transfers.CategoryTransfer;
import com.curevent.models.transfers.EventTransfer;
import com.curevent.services.CategoryService;
import com.curevent.utils.mapping.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@Transactional
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
        Category category = categoryService.getOneById(id);
        return mapper.toTransfer(category);
    }

    @PostMapping("/")
    public CategoryTransfer addCategory(@RequestBody @Valid CategoryTransfer categoryTransfer) {
        Category category = mapper.toEntity(categoryTransfer);
        return mapper.toTransfer(categoryService.add(category));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/")
    public CategoryTransfer editCategory(@RequestBody CategoryTransfer categoryTransfer) {
        Category category = mapper.toEntity(categoryTransfer);
        return mapper.toTransfer(categoryService.update(category));
    }
}
