package com.demo.fulfillment_service.inventory.controller.v1.admin;

import com.demo.fulfillment_service.inventory.dto.admin.AdminProductRequestDTO;
import com.demo.fulfillment_service.inventory.dto.admin.AdminProductResponseDTO;
import com.demo.fulfillment_service.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class AdminProductsController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<AdminProductResponseDTO>> index() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminProductResponseDTO> show(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findbyId(id));
    }

    @PostMapping
    public ResponseEntity<AdminProductResponseDTO> create(
            @Valid @RequestBody AdminProductRequestDTO adminProductRequestDTO) {

        return ResponseEntity.ok(productService.create(adminProductRequestDTO));
    }

}
