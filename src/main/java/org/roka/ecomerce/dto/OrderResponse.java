package org.roka.ecomerce.dto;

import org.roka.ecomerce.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        BigDecimal totalPrice,
        Status status,
        LocalDateTime orderedAt,
        List<OrderItemResponse> items
) {
}
