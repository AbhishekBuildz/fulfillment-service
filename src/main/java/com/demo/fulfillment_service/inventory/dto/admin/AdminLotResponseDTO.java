package com.demo.fulfillment_service.inventory.dto.admin;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AdminLotResponseDTO(
        Long id,
        Long productId,
        Long warehouseId,
        Long availableQuantity,
        Long reservedQuantity,
        LocalDate expiryDate
) {}
