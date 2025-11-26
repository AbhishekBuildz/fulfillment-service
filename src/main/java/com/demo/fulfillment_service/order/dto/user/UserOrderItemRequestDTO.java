package com.demo.fulfillment_service.order.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserOrderItemRequestDTO(
        @NotNull Long productId,
        @NotNull @Positive @Max(100) Long quantity  // max limit enforced here
) {}
