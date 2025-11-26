package com.demo.fulfillment_service.inventory.controller.v1.admin;

import com.demo.fulfillment_service.inventory.dto.admin.AdminWarehouseRequestDTO;
import com.demo.fulfillment_service.inventory.dto.admin.AdminWarehouseResponseDTO;
import com.demo.fulfillment_service.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/warehouses")
public class AdminWarehousesController {

    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<AdminWarehouseResponseDTO>> index() {

        return ResponseEntity.ok(warehouseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminWarehouseResponseDTO> show(@PathVariable Long id) {

        return ResponseEntity.ok(warehouseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AdminWarehouseResponseDTO> create(
            @Valid @RequestBody AdminWarehouseRequestDTO adminWarehouseRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(warehouseService.create(adminWarehouseRequestDTO));
    }
}
