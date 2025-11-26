package com.demo.fulfillment_service.user.model;

import com.demo.fulfillment_service.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseModel {

    @Column(nullable = false)
    private String name;

//    @Column(nullable = false, unique = true)
//    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

}