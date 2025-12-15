package org.roka.ecomerce.dto;

public record AuthRequest(
        String email,
        String password
) {
}
