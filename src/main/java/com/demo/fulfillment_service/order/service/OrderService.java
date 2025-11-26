package com.demo.fulfillment_service.order.service;

import com.demo.fulfillment_service.inventory.model.Product;
import com.demo.fulfillment_service.inventory.repository.ProductRepository;
import com.demo.fulfillment_service.inventory.service.InventoryService;
import com.demo.fulfillment_service.order.dto.user.UserOrderItemRequestDTO;
import com.demo.fulfillment_service.order.dto.user.UserOrderRequestDTO;
import com.demo.fulfillment_service.order.dto.user.UserOrderResponseDTO;
import com.demo.fulfillment_service.order.mapper.OrderMapper;
import com.demo.fulfillment_service.order.model.Order;
import com.demo.fulfillment_service.order.model.OrderItem;
import com.demo.fulfillment_service.order.repository.OrderRepository;
import com.demo.fulfillment_service.user.model.User;
import com.demo.fulfillment_service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Long DEFAULT_WAREHOUSE_ID = 1L; // single warehouse assumption

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final InventoryService inventoryService;
    private final OrderMapper orderMapper;

    // ---------- create (user) ----------
    @Transactional
    public UserOrderResponseDTO create(UserOrderRequestDTO userOrderRequestDTO) {

        validateProductsAreUnique(userOrderRequestDTO);

        User user = userRepository.findById(userOrderRequestDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with id %d not found.", userOrderRequestDTO.userId())));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);

        for (UserOrderItemRequestDTO userOrderItemDTO : userOrderRequestDTO.items()) {

            Product product = productRepository.findById(userOrderItemDTO.productId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Product with id %d not found.", userOrderItemDTO.productId())));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(userOrderItemDTO.quantity());

            order.getItems().add(orderItem);
        }

        Order savedOrder = orderRepository.save(order);

        // productId -> (lotId -> quantityUsed)
        Map<Long, Map<Long, Long>> lotUsageByProductId = new HashMap<>();

        for (OrderItem orderItem : savedOrder.getItems()) {
            Map<Long, Long> usedLots = inventoryService.reserveProductFromLots(DEFAULT_WAREHOUSE_ID, orderItem);

            Long productId = orderItem.getProduct().getId();
            lotUsageByProductId.put(productId, usedLots);
        }

        return orderMapper.toUserOrderResponseDTO(savedOrder, lotUsageByProductId);
    }

//    @Transactional
//    public UserOrderResponseDTO confirm(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
//
//        if (order.getStatus() != Order.Status.PENDING) {
//            throw new IllegalArgumentException("Only PENDING orders can be confirmed");
//        }
//
//        order.setStatus(Order.Status.CONFIRMED);
//        Order savedOrder = orderRepository.save(order);
//
//        return orderMapper.toUserOrderResponseDTO(savedOrder);
//    }
//
//    @Transactional
//    public UserOrderResponseDTO cancel(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
//
//        if (order.getStatus() != Order.Status.PENDING) {
//            throw new IllegalArgumentException("Only PENDING orders can be canceled");
//        }
//
//        order.setStatus(Order.Status.CANCELED);
//        Order savedOrder = orderRepository.save(order);
//
//        // (Optional) here you could implement "un-reserve" logic
//
//        return orderMapper.toUserOrderResponseDTO(savedOrder);
//    }

    // ---------------------- VALIDATIONS ---------------------------

    private void validateProductsAreUnique(UserOrderRequestDTO userOrderRequestDTO) {
        Set<Long> productSet = new HashSet<>();

        for (UserOrderItemRequestDTO item : userOrderRequestDTO.items()) {

            if (!productSet.add(item.productId())) {

                throw new IllegalArgumentException(
                        "Duplicate productId: " + item.productId() +
                                " in order. Combine quantities into a single item."
                );
            }
        }
    }

}
