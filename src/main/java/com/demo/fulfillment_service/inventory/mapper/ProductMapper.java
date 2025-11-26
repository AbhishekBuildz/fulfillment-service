package com.demo.fulfillment_service.inventory.mapper;

import com.demo.fulfillment_service.inventory.dto.admin.AdminProductRequestDTO;
import com.demo.fulfillment_service.inventory.dto.admin.AdminProductResponseDTO;
import com.demo.fulfillment_service.inventory.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public AdminProductResponseDTO toAdminProductResponseDTO(Product product) {

        return AdminProductResponseDTO.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public Product toEntity(AdminProductRequestDTO adminProductRequestDTO) {

        return Product.builder()
                .sku(adminProductRequestDTO.sku())
                .name(adminProductRequestDTO.name())
                .description(adminProductRequestDTO.description())
                .price(adminProductRequestDTO.price())
                .active(true)
                .build();
    }
}
