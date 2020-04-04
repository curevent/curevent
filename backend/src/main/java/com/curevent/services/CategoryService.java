package com.curevent.services;

import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Category;
import com.curevent.models.transfers.CategoryTransfer;
import com.curevent.repositories.CategoryRepository;
import com.curevent.utils.mapping.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final CategoryMapper mapper;

    private Category getEntityById(Long id) {
        return categoryRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Category" + id));
    }

    public CategoryTransfer getOneById(Long id) {
        Category category = categoryRepository.findById(id).stream().findAny()
                .orElseThrow(() -> new NotFoundException("No such Category"+id));
        return mapper.toTransfer(category);
    }

    public CategoryTransfer add(CategoryTransfer categoryTransfer) {
        Category category = mapper.toEntity(categoryTransfer);
        return mapper.toTransfer(categoryRepository.save(category));
    }

    public void delete(Long id) {
        Category category = getEntityById(id);
        categoryRepository.delete(category);
    }

    public CategoryTransfer update(CategoryTransfer categoryTransfer) {
        Category category = mapper.toEntity(categoryTransfer);
        if (!categoryRepository.existsById(category.getId())) {
            throw new NotFoundException("No such Category" + category.getId());
        }
        return mapper.toTransfer(categoryRepository.save(category));
    }
}
