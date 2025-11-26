package com.demo.fulfillment_service.inventory.service;

import com.demo.fulfillment_service.inventory.dto.admin.AdminWarehouseRequestDTO;
import com.demo.fulfillment_service.inventory.dto.admin.AdminWarehouseResponseDTO;
import com.demo.fulfillment_service.inventory.mapper.WarehouseMapper;
import com.demo.fulfillment_service.inventory.model.Warehouse;
import com.demo.fulfillment_service.inventory.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    public List<AdminWarehouseResponseDTO> findAll() {

        return warehouseRepository.findAll()
                .stream()
                .map(warehouseMapper::toAdminWarehouseResponseDTO)
                .toList();
    }

    public AdminWarehouseResponseDTO findById(Long id) {

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + id));

        return warehouseMapper.toAdminWarehouseResponseDTO(warehouse);
    }

    public AdminWarehouseResponseDTO create(AdminWarehouseRequestDTO adminWarehouseRequestDTO) {

        Warehouse warehouse = warehouseMapper.toEntity(adminWarehouseRequestDTO);
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        return warehouseMapper.toAdminWarehouseResponseDTO(savedWarehouse);
    }
}
