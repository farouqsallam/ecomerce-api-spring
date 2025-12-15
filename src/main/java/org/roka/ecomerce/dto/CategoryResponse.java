package org.roka.ecomerce.dto;

import org.roka.ecomerce.entity.Product;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        String description
) {}
