package com.demo.fulfillment_service.inventory.mapper;

import com.demo.fulfillment_service.inventory.dto.admin.AdminWarehouseRequestDTO;
import com.demo.fulfillment_service.inventory.dto.admin.AdminWarehouseResponseDTO;
import com.demo.fulfillment_service.inventory.model.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

    public Warehouse toEntity(AdminWarehouseRequestDTO adminWarehouseRequestDTO) {

        return Warehouse.builder()
                .name(adminWarehouseRequestDTO.name())
                .address(adminWarehouseRequestDTO.address())
                .latitude(adminWarehouseRequestDTO.latitude())
                .longitude(adminWarehouseRequestDTO.longitude())
                .type(adminWarehouseRequestDTO.type())
                .build();
    }

    public AdminWarehouseResponseDTO toAdminWarehouseResponseDTO(Warehouse warehouse) {

        return AdminWarehouseResponseDTO.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .address(warehouse.getAddress())
                .latitude(warehouse.getLatitude())
                .longitude(warehouse.getLongitude())
                .type(warehouse.getType())
                .build();
    }
}
