package com.demo.fulfillment_service.inventory.service;

import com.demo.fulfillment_service.inventory.dto.admin.AdminProductRequestDTO;
import com.demo.fulfillment_service.inventory.dto.admin.AdminProductResponseDTO;
import com.demo.fulfillment_service.inventory.mapper.ProductMapper;
import com.demo.fulfillment_service.inventory.model.Product;
import com.demo.fulfillment_service.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<AdminProductResponseDTO> findAll() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toAdminProductResponseDTO)
                .toList();
    }

    // keep the name matching your controller: findbyId
    public AdminProductResponseDTO findbyId(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with id %d not found.", id)));

        return productMapper.toAdminProductResponseDTO(product);
    }

    public AdminProductResponseDTO create(AdminProductRequestDTO adminProductRequestDTO) {

        validateSkuIsUnique(adminProductRequestDTO);

        Product product = productMapper.toEntity(adminProductRequestDTO);
        Product savedProduct = productRepository.save(product);

        return productMapper.toAdminProductResponseDTO(savedProduct);
    }

    // ------------------------ VALIDATIONS -----------------------------

    public void validateSkuIsUnique(AdminProductRequestDTO adminProductRequestDTO) {

        if (productRepository.existsBySku(adminProductRequestDTO.sku())) {

            throw new IllegalArgumentException(
                    "A product with SKU '" + adminProductRequestDTO.sku() + "' already exists."
            );
        }
    }

}
