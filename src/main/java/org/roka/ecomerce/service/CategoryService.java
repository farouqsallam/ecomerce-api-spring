package org.roka.ecomerce.service;

import org.roka.ecomerce.dto.CategoryRequest;
import org.roka.ecomerce.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    // Create
    CategoryResponse createCategory(CategoryRequest request);

    // Read
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();

    // Update
    CategoryResponse updateCategory(Long id, CategoryRequest request);

    // Delete
    void deleteCategory(Long id);
}