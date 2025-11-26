package com.demo.fulfillment_service.inventory.dto.admin;

import jakarta.validation.constraints.NotBlank;

public record AdminWarehouseRequestDTO(
        @NotBlank String name,
        String address,
        Double latitude,
        Double longitude,
        String type
) {}
