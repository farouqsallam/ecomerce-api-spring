package org.roka.ecomerce.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roka.ecomerce.dto.ProductRequest;
import org.roka.ecomerce.dto.ProductResponse;
import org.roka.ecomerce.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequest request);
}
