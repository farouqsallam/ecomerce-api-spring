package org.roka.ecomerce.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.roka.ecomerce.dto.CategoryResponse;
import org.roka.ecomerce.dto.ProductRequest;
import org.roka.ecomerce.dto.ProductResponse;
import org.roka.ecomerce.service.CategoryService;
import org.roka.ecomerce.service.ProductService;
import org.roka.ecomerce.service.impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;




    @PostMapping
    ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getProduct(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request){
        return new ResponseEntity<>(productService.updateProduct(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



















