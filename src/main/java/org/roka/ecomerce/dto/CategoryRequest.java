package org.roka.ecomerce.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "name must be provided")
        String name,
        String description
) {
}
