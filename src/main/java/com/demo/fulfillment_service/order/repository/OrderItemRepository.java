package com.demo.fulfillment_service.order.repository;

import com.demo.fulfillment_service.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
