package com.demo.fulfillment_service.inventory.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AdminProductRequestDTO(
        @NotBlank String sku,
        @NotBlank String name,
        String description,
        @NotNull @Positive Double price
) {}
