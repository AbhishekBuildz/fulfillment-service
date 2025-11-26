
package com.demo.fulfillment_service.inventory.repository;

import com.demo.fulfillment_service.inventory.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface LotRepository extends JpaRepository<Lot, Long> {

    List<Lot> findByWarehouseId(Long warehouseId);
    List<Lot> findByWarehouseIdAndProductId(Long warehouseId, Long productId);

    @Query("""
            SELECT COALESCE(SUM(l.availableQuantity), 0)
            FROM Lot l
            WHERE l.warehouse.id = :warehouseId
              AND l.product.id = :productId
              AND l.expiryDate > :cutoffDate
            """)
    Long getTotalAvailableQuantity(Long warehouseId,
                                   Long productId,
                                   LocalDate cutoffDate);

    @Query("""
           SELECT l FROM Lot l
           WHERE l.warehouse.id = :warehouseId
             AND l.product.id = :productId
             AND l.availableQuantity > 0
             AND l.expiryDate > :cutoffDate
           ORDER BY l.expiryDate ASC
           """)
    Stream<Lot> streamLotsForFefo(Long warehouseId,
                                  Long productId,
                                  LocalDate cutoffDate);

}
