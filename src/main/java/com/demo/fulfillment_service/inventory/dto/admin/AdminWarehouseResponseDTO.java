package com.demo.fulfillment_service.inventory.dto.admin;

import lombok.Builder;

@Builder
public record AdminWarehouseResponseDTO(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        String type
) {}
