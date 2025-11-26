package com.demo.fulfillment_service.inventory.service;

import com.demo.fulfillment_service.exception.InsufficientInventoryException;
import com.demo.fulfillment_service.inventory.dto.admin.AdminLotRequestDTO;
import com.demo.fulfillment_service.inventory.dto.admin.AdminLotResponseDTO;
import com.demo.fulfillment_service.inventory.mapper.LotMapper;
import com.demo.fulfillment_service.inventory.model.Lot;
import com.demo.fulfillment_service.inventory.model.Product;
import com.demo.fulfillment_service.inventory.model.Warehouse;
import com.demo.fulfillment_service.inventory.repository.LotRepository;
import com.demo.fulfillment_service.inventory.repository.ProductRepository;
import com.demo.fulfillment_service.inventory.repository.WarehouseRepository;
import com.demo.fulfillment_service.order.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final LotMapper lotMapper;
    private final LotRepository lotRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    private static final int EXPIRY_BUFFER_DAYS = 1;

    // ---------- Admin-facing ----------

    public AdminLotResponseDTO createLot(Long warehouseId, AdminLotRequestDTO adminLotRequestDTO) {

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Warehouse with id %d not found.", warehouseId)));

        Product product = productRepository.findById(adminLotRequestDTO.productId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with id %d not found.", adminLotRequestDTO.productId())));

        Lot lot = Lot.builder()
                .product(product)
                .warehouse(warehouse)
                .expiryDate(adminLotRequestDTO.expiryDate())
                .availableQuantity(adminLotRequestDTO.quantity())
                .reservedQuantity(0L)
                .build();

        Lot savedLot = lotRepository.save(lot);
        return lotMapper.toAdminLotResponseDTO(savedLot);
    }

    public List<AdminLotResponseDTO> findLotsByWarehouseId(Long warehouseId) {

        return lotRepository.findByWarehouseId(warehouseId)
                .stream()
                .map(lotMapper::toAdminLotResponseDTO)
                .toList();
    }

    @Transactional
    public Map<Long, Long> reserveProductFromLots(Long warehouseId, OrderItem orderItem) {
        Map<Long, Long> usedLots = new LinkedHashMap<>();

        Long productId = orderItem.getProduct().getId();
        long requiredQuantity = orderItem.getQuantity();

        LocalDate cutoffDate = LocalDate.now().plusDays(EXPIRY_BUFFER_DAYS);

        Long totalAvailable = lotRepository.getTotalAvailableQuantity(
                warehouseId,
                productId,
                cutoffDate
        );

        if (totalAvailable == null || totalAvailable < requiredQuantity) {
            throw new InsufficientInventoryException(
                    "Insufficient inventory for productId: " + productId
            );
        }

        long remaining = requiredQuantity;

        try (Stream<Lot> stream = lotRepository.streamLotsForFefo(warehouseId, productId, cutoffDate)) {

            Iterator<Lot> iterator = stream.iterator();

            while (iterator.hasNext() && remaining > 0) {
                Lot lot = iterator.next();

                long availableQuantity = lot.getAvailableQuantity();
                if (availableQuantity <= 0) {
                    continue;
                }

                long quantityToReserve = Math.min(availableQuantity, remaining);

                lot.setAvailableQuantity(availableQuantity - quantityToReserve);
                lot.setReservedQuantity(lot.getReservedQuantity() + quantityToReserve);

                // track how much of this lot was used
                usedLots.merge(lot.getId(), quantityToReserve, Long::sum);

                remaining -= quantityToReserve;
            }
        }

        if (remaining > 0) {
            throw new InsufficientInventoryException(
                    "Inventory inconsistency detected during allocation for product: " + productId
            );
        }

        return usedLots;
    }

}
