package org.roka.ecomerce.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        BigDecimal price,
        Integer quantity,
        String productName,
        Long productId
) {
}
