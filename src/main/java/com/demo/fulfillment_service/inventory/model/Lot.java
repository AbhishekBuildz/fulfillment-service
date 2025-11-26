package com.demo.fulfillment_service.inventory.model;

import com.demo.fulfillment_service.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lots")
public class Lot extends BaseModel {

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private Long availableQuantity;

    @Column(nullable = false)
    private Long reservedQuantity = 0L;

    @Version
    private Long version; // optimistic locking for concurrency

}