package org.roka.ecomerce.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.roka.ecomerce.dto.CategoryRequest;
import org.roka.ecomerce.dto.CategoryResponse;
import org.roka.ecomerce.entity.Category;
import org.roka.ecomerce.exception.ResourceNotFoundException;
import org.roka.ecomerce.mapper.CategoryMapper;
import org.roka.ecomerce.repo.CategoryRepo;
import org.roka.ecomerce.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl  implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper mapper;



    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = mapper.toEntity(request);

        Category saved = categoryRepo.save(category);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id: " + id)
        );
        return mapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepo.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with id: " + id)
        );
        category.setName(request.name());
        category.setDescription(request.description());

        Category saved = categoryRepo.save(category);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if(!categoryRepo.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepo.deleteById(id);

    }
}
