package org.roka.ecomerce.dto;

import java.util.List;

public record OrderRequest(
        List<OrderItemRequest> items
) {
}
