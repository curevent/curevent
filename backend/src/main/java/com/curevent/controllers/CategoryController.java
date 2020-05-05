package com.curevent.controllers;

import com.curevent.models.transfers.CategoryTransfer;
import com.curevent.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryTransfer getCategory(@PathVariable Long id) {
        return categoryService.getOneById(id);
    }

    @PostMapping("/")
    public CategoryTransfer addCategory(@RequestBody @Valid CategoryTransfer categoryTransfer) {
        return categoryService.add(categoryTransfer);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/")
    public CategoryTransfer editCategory(@RequestBody CategoryTransfer categoryTransfer) {
        return categoryService.update(categoryTransfer);
    }
}
