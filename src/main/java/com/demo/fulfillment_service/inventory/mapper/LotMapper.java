package com.demo.fulfillment_service.inventory.mapper;

import com.demo.fulfillment_service.inventory.dto.admin.AdminLotResponseDTO;
import com.demo.fulfillment_service.inventory.model.Lot;
import org.springframework.stereotype.Component;

@Component
public class LotMapper {

    public AdminLotResponseDTO toAdminLotResponseDTO(Lot lot) {

        return AdminLotResponseDTO.builder()
                .id(lot.getId())
                .productId(lot.getProduct().getId())
                .warehouseId(lot.getWarehouse().getId())
                .availableQuantity(lot.getAvailableQuantity())
                .reservedQuantity(lot.getReservedQuantity())
                .expiryDate(lot.getExpiryDate())
                .build();
    }
}
