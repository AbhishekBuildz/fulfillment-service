package com.demo.fulfillment_service.order.repository;

import com.demo.fulfillment_service.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
