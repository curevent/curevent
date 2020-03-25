package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Category;
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

    public Category getOneById(Long id) {
        return categoryRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Category" + id));
    }

    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = getOneById(id);
        categoryRepository.delete(category);
    }

    public Category update(Category category) {
        if (!categoryRepository.existsById(category.getId())) {
            throw new NotFoundException("No such Category" + category.getId());
        }
        return categoryRepository.save(category);
    }
}
