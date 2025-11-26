package com.demo.fulfillment_service.inventory.dto.admin;

import lombok.Builder;

@Builder
public record AdminProductResponseDTO(
        Long id,
        String sku,
        String name,
        String description,
        Double price
) {}
