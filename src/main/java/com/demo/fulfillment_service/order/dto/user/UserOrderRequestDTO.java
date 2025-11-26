package com.demo.fulfillment_service.order.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserOrderRequestDTO(
        @NotNull Long userId,
        @NotEmpty List<@Valid UserOrderItemRequestDTO> items
) {}
