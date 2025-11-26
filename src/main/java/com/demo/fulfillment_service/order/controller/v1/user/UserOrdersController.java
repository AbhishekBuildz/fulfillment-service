package com.demo.fulfillment_service.order.controller.v1.user;

import com.demo.fulfillment_service.order.dto.user.UserOrderRequestDTO;
import com.demo.fulfillment_service.order.dto.user.UserOrderResponseDTO;
import com.demo.fulfillment_service.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/orders")
public class UserOrdersController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<UserOrderResponseDTO> create(
            @Valid @RequestBody UserOrderRequestDTO userOrderRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.create(userOrderRequestDTO));
    }

//    @PostMapping("/{orderId}/confirm")
//    public ResponseEntity<UserOrderResponseDTO> confirm(@PathVariable Long orderId) {
//        return ResponseEntity.ok(orderService.confirm(orderId));
//    }
//
//    @PostMapping("/{orderId}/cancel")
//    public ResponseEntity<UserOrderResponseDTO> cancel(@PathVariable Long orderId) {
//        return ResponseEntity.ok(orderService.cancel(orderId));
//    }
}
