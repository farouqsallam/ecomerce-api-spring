package org.roka.ecomerce.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.roka.ecomerce.dto.ProductRequest;
import org.roka.ecomerce.dto.ProductResponse;
import org.roka.ecomerce.entity.Category;
import org.roka.ecomerce.entity.Product;
import org.roka.ecomerce.exception.ResourceNotFoundException;
import org.roka.ecomerce.mapper.ProductMapper;
import org.roka.ecomerce.repo.CategoryRepo;
import org.roka.ecomerce.repo.ProductRepo;
import org.roka.ecomerce.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ProductMapper mapper;


    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating product request: {}, CatID= {}", request.name(), request.categoryId());

        Category category = categoryRepo.findById(request.categoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category with id " + request.categoryId() + " not found")
        );

        Product product = mapper.toEntity(request);
        product.setCategory(category);

        Product saved = productRepo.save(product);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {

        Product product = productRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + id + " not found")
        );
        return mapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
        return productRepo.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + id + " not found")
        );

        Category category = categoryRepo.findById(request.categoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category with id " + request.categoryId() + " not found")
        );

        product.setSku(request.sku());
        product.setPrice(request.price());
        product.setName(request.name());
        product.setStock(request.stock());
        product.setCategory(category);

        Product updated = productRepo.save(product);

        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if(!productRepo.existsById(id)) {
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }
        productRepo.deleteById(id);
    }
}
