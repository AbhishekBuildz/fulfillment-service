package com.demo.fulfillment_service.inventory.controller.v1.admin;

import com.demo.fulfillment_service.inventory.dto.admin.AdminLotRequestDTO;
import com.demo.fulfillment_service.inventory.dto.admin.AdminLotResponseDTO;
import com.demo.fulfillment_service.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/warehouses/{warehouseId}/lots")
public class AdminLotsController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<AdminLotResponseDTO>> index(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(inventoryService.findLotsByWarehouseId(warehouseId));
    }

    @PostMapping
    public ResponseEntity<AdminLotResponseDTO> create(
            @PathVariable Long warehouseId,
            @Valid @RequestBody AdminLotRequestDTO adminLotRequestDTO
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.createLot(warehouseId, adminLotRequestDTO));
    }
}
