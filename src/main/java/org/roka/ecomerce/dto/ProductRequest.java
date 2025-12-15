package org.roka.ecomerce.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name is requeired")
        String name,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price,
        @Min(value = 0, message = "sku can't be negative")
        @NotNull(message = "Stock is required")
        Integer stock,
        @NotBlank(message = "SKU is required")
        String sku,
        @NotNull(message = "Category is required")
        Long categoryId

) {
}
