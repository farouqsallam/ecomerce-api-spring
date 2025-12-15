package org.roka.ecomerce.service;

import org.roka.ecomerce.dto.ProductRequest;
import org.roka.ecomerce.dto.ProductResponse;
import org.roka.ecomerce.entity.Product;
import java.util.List;

public interface ProductService {

    //Create
    ProductResponse createProduct(ProductRequest request);

    //Read
    ProductResponse getProduct(Long id);
    List<ProductResponse> getProducts();


    //Update
    ProductResponse updateProduct(Long id, ProductRequest request);

    //Delete
    void deleteProduct(Long id);

}
