package com.demo.fulfillment_service.inventory.model;


import com.demo.fulfillment_service.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouses")
public class Warehouse extends BaseModel {

    @Column(nullable = false)
    private String name;

    private String address;
    private Double latitude;
    private Double longitude;
    private String type;

}
