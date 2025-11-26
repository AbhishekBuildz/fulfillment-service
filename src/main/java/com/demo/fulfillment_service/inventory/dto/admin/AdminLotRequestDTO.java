package com.demo.fulfillment_service.inventory.dto.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record AdminLotRequestDTO(
        @NotNull Long productId,
        @NotNull @Positive Long quantity,
        @NotNull LocalDate expiryDate
) {}
