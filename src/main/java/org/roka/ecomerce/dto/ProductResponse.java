package org.roka.ecomerce.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String sku,
        BigDecimal price,
        Integer stock,
        Long categoryId,
        String categoryName
) {
}