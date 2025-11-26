package com.demo.fulfillment_service.order.dto.user;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record UserOrderResponseDTO(
        Long id,
        Long userId,
        String status,
        List<Item> items,
        Instant createdAt,
        Instant updatedAt
) {
    @Builder
    public record Item(
            Long productId,
            Long quantity,
            List<LotUsage> lotsUsed
    ) {}

    @Builder
    public record LotUsage(
            Long lotId,
            Long quantityUsed
    ) {}
}
