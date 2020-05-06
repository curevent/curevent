package com.curevent.services;

import com.curevent.exceptions.ConflictException;
import com.curevent.exceptions.InvalidArgumentException;
import com.curevent.exceptions.NotFoundException;
import com.curevent.models.entities.Category;
import com.curevent.models.enums.DefaultCategory;
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
        if (id.equals(DefaultCategory.PRIVATE.id()) || id.equals(DefaultCategory.ALL_FRIENDS.id())) {
            throw new InvalidArgumentException("Forbidden to delete default category " + id);
        }
        Category category = getEntityById(id);
        categoryRepository.delete(category);
    }

    public CategoryTransfer update(CategoryTransfer categoryTransfer) {
        if (categoryTransfer.getId().equals(DefaultCategory.PRIVATE.id()) || categoryTransfer.getId().equals(DefaultCategory.ALL_FRIENDS.id())) {
            throw new InvalidArgumentException("Forbidden to edit default category " + categoryTransfer.getId());
        }
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

    public List<CategoryTransfer> getDefaultCategories() {
        return List.of(getOneById(DefaultCategory.PRIVATE.id()), (getOneById(DefaultCategory.ALL_FRIENDS.id())));
    }
}
