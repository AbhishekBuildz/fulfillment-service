package com.demo.fulfillment_service.inventory.model;

import com.demo.fulfillment_service.base.BaseModel;
import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseModel {

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    private boolean active = true;

}