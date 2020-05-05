package com.curevent.services;

import com.curevent.exceptions.ConflictException;
import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Category;
import com.curevent.models.transfers.CategoryTransfer;
import com.curevent.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class CategoryService {
    public static final Long DEFAULT_CATEGORY_PRIVATE_ID = 0L;
    public static final Long DEFAULT_CATEGORY_ALL_FRIENDS_ID = 1L;

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    Category getEntityById(Long id) {
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
        Optional<Category> equalsCategory= categoryRepository.findEqualsCategory(category.getOwnerId(),
                category.getDescription());
        if(equalsCategory.isPresent()) {
            throw new ConflictException("Category with ownerId " + category.getOwnerId() +
                    "and description " + category.getDescription() + " already exists");
        }
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
        Optional<Category> equalsCategory= categoryRepository.findEqualsCategory(category.getOwnerId(),
                category.getDescription());
        if(equalsCategory.isPresent() && !equalsCategory.get().getId().equals(category.getId())) {
            throw new ConflictException("Category with ownerId " + category.getOwnerId() +
                    "and description " + category.getDescription() + " already exists");
        }
        return mapper.map(categoryRepository.save(category), CategoryTransfer.class);
    }

    public List<Category> getDefaultCategories() {
        return List.of(getEntityById(DEFAULT_CATEGORY_PRIVATE_ID), (getEntityById(DEFAULT_CATEGORY_ALL_FRIENDS_ID)));
    }
}
