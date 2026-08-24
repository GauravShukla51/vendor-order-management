package com.vendor.controller;

import com.vendor.dto.CreateOrderRequest;
import com.vendor.entity.RestaurantOrder;
import com.vendor.service.RestaurantOrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class RestaurantOrderController {

    private final RestaurantOrderService orderService;

    public RestaurantOrderController(RestaurantOrderService orderService) {
        this.orderService = orderService;
    }

    // Create Order
    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantOrder> createOrder(
            @PathVariable Long restaurantId,
            @RequestBody CreateOrderRequest request) {

        return new ResponseEntity<>(
                orderService.createOrder(restaurantId, request),
                HttpStatus.CREATED
        );
    }

    // Confirm Order
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<RestaurantOrder> confirmOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.confirmOrder(id)
        );
    }

    // Mark Order Out For Delivery
    @PatchMapping("/{id}/out-for-delivery")
    public ResponseEntity<RestaurantOrder> markOutForDelivery(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.markOutForDelivery(id)
        );
    }

    // Mark Order Delivered
    @PatchMapping("/{id}/delivered")
    public ResponseEntity<RestaurantOrder> markDelivered(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.markDelivered(id)
        );
    }

    // Cancel Order
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<RestaurantOrder> cancelOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.cancelOrder(id)
        );
    }

    // View All Orders
    @GetMapping
    public ResponseEntity<List<RestaurantOrder>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }

    // Top 5 Vegetables Ordered In Last 30 Days
    @GetMapping("/top-vegetables")
    public ResponseEntity<List<Map<String, Object>>> getTop5Vegetables() {

        return ResponseEntity.ok(
                orderService.getTop5VegetablesLast30Days()
        );
    }

    // View Order By ID
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantOrder> getOrderById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.getOrderById(id)
        );
    }

    // View Orders By Restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<RestaurantOrder>> getOrdersByRestaurant(
            @PathVariable Long restaurantId) {

        return ResponseEntity.ok(
                orderService.getOrdersByRestaurant(restaurantId)
        );
    }

}