package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Category;
import com.curevent.models.transfers.CategoryTransfer;
import com.curevent.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    private Category getEntityById(Long id) {
        return categoryRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Category" + id));
    }

    public CategoryTransfer getOneById(Long id) {
        Category category = categoryRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Category"+id));
        return mapper.map(category, CategoryTransfer.class);
    }

    public CategoryTransfer add(CategoryTransfer categoryTransfer) {
        Category category = mapper.map(categoryTransfer, Category.class);
        return mapper.map(categoryRepository.save(category), CategoryTransfer.class);
    }

    public void delete(Long id) {
        Category category = getEntityById(id);
        categoryRepository.delete(category);
    }

    public CategoryTransfer update(CategoryTransfer categoryTransfer) {
        Category category = mapper.map(categoryTransfer, Category.class);
        if (!categoryRepository.existsById(category.getId())) {
            throw new NotFoundException("No such Category" + category.getId());
        }
        return mapper.map(categoryRepository.save(category), CategoryTransfer.class);
    }
}
