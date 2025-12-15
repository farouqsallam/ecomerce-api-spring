package org.roka.ecomerce.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.roka.ecomerce.dto.CategoryRequest;
import org.roka.ecomerce.dto.CategoryResponse;
import org.roka.ecomerce.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryRequest request);

}
