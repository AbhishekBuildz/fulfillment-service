package com.demo.fulfillment_service.order.mapper;

import com.demo.fulfillment_service.order.dto.user.UserOrderResponseDTO;
import com.demo.fulfillment_service.order.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderMapper {

    public UserOrderResponseDTO toUserOrderResponseDTO(Order order, Map<Long, Map<Long, Long>> lotUsageByProductId) {

        List<UserOrderResponseDTO.Item> items = order.getItems().stream()
                .map(orderItem -> {
                    Long productId = orderItem.getProduct().getId();

                    Map<Long, Long> lotUsage =
                            lotUsageByProductId.getOrDefault(productId, Map.of());

                    List<UserOrderResponseDTO.LotUsage> lotsUsed = lotUsage.entrySet()
                            .stream()
                            .map(entry -> UserOrderResponseDTO.LotUsage.builder()
                                    .lotId(entry.getKey())
                                    .quantityUsed(entry.getValue())
                                    .build())
                            .toList();

                    return UserOrderResponseDTO.Item.builder()
                            .productId(productId)
                            .quantity(orderItem.getQuantity())
                            .lotsUsed(lotsUsed)
                            .build();
                })
                .toList();

        return UserOrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .status(order.getStatus().name())
                .items(items)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

}
