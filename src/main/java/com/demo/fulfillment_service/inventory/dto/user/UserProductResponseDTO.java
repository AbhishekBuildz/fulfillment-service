package com.demo.fulfillment_service.inventory.dto.user;

public record UserProductResponseDTO(
        Long id,
        String sku,
        String name,
        String description,
        Double price
) {}
