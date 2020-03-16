package com.curevent.services;

import com.curevent.models.entities.CategoryEntity;
import com.curevent.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity getOneById(Long id) {
        return categoryRepository.findById(id).stream().findAny().orElse(null);
    }

    public void add(CategoryEntity category) {
        categoryRepository.save(category);
    }
}
