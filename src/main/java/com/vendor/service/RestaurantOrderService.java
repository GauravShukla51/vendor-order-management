package com.vendor.service;

import com.vendor.dto.CreateOrderRequest;
import com.vendor.dto.OrderItemRequest;
import com.vendor.entity.Inventory;
import com.vendor.entity.OrderItem;
import com.vendor.entity.OrderStatus;
import com.vendor.entity.Restaurant;
import com.vendor.entity.RestaurantOrder;
import com.vendor.entity.Vegetable;
import com.vendor.repository.InventoryRepository;
import com.vendor.repository.OrderItemRepository;
import com.vendor.repository.RestaurantOrderRepository;
import com.vendor.repository.RestaurantRepository;
import com.vendor.repository.VegetableRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantOrderService {

    private final RestaurantOrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final InventoryRepository inventoryRepository;
    private final VegetableRepository vegetableRepository;
    private final OrderItemRepository orderItemRepository;

    public RestaurantOrderService(
            RestaurantOrderRepository orderRepository,
            RestaurantRepository restaurantRepository,
            InventoryRepository inventoryRepository,
            VegetableRepository vegetableRepository,
            OrderItemRepository orderItemRepository) {

        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.inventoryRepository = inventoryRepository;
        this.vegetableRepository = vegetableRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // Create Order

    @Transactional
    public RestaurantOrder createOrder(
            Long restaurantId,
            CreateOrderRequest request) {

        // 1. Find restaurant
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Restaurant not found with id: " + restaurantId));

        // 2. Check restaurant active
        if (!restaurant.isActive()) {
            throw new RuntimeException("Restaurant is inactive");
        }

        // 3. Validate order items
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException(
                    "Order must contain at least one item");
        }

        // 4. Create order
        RestaurantOrder order = new RestaurantOrder();
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.PLACED);

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 5. Process each vegetable
        for (OrderItemRequest itemRequest : request.getItems()) {

            if (itemRequest.getVegetableName() == null ||
                    itemRequest.getVegetableName().isBlank()) {

                throw new RuntimeException(
                        "Vegetable name is required");
            }

            if (itemRequest.getQuantity() == null ||
                    itemRequest.getQuantity()
                            .compareTo(BigDecimal.ZERO) <= 0) {

                throw new RuntimeException(
                        "Quantity must be greater than zero");
            }

            if (itemRequest.getUnit() == null ||
                    itemRequest.getUnit().isBlank()) {

                throw new RuntimeException(
                        "Unit is required");
            }

            // Find vegetable
            Vegetable vegetable = vegetableRepository
                    .findByNameIgnoreCase(
                            itemRequest.getVegetableName())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Vegetable not found: "
                                            + itemRequest.getVegetableName()));

            // Find today's inventory
            Inventory inventory = inventoryRepository
                    .findFirstByVegetableIdAndInventoryDateOrderByIdAsc(
                            vegetable.getId(),
                            LocalDate.now())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "No inventory available for vegetable: "
                                            + vegetable.getName()));

            // Check unit
            if (!inventory.getUnit()
                    .equalsIgnoreCase(itemRequest.getUnit())) {

                throw new RuntimeException(
                        "Unit mismatch for vegetable: "
                                + vegetable.getName());
            }

            // Check sufficient inventory
            if (inventory.getQuantity()
                    .compareTo(itemRequest.getQuantity()) < 0) {

                throw new RuntimeException(
                        "Insufficient inventory for vegetable: "
                                + vegetable.getName()
                                + ". Available: "
                                + inventory.getQuantity()
                                + " "
                                + inventory.getUnit());
            }

            // Calculate subtotal using backend price
            BigDecimal subtotal = inventory.getPrice()
                    .multiply(itemRequest.getQuantity());

            // Create order item
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setVegetable(vegetable);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnit(itemRequest.getUnit());

            // Price comes from inventory, NOT client
            orderItem.setPrice(inventory.getPrice());
            orderItem.setSubtotal(subtotal);

            order.getItems().add(orderItem);

            // Add to total
            totalAmount = totalAmount.add(subtotal);
        }

        // 6. Backend calculates total
        order.setTotalAmount(totalAmount);

        // 7. Save order
        return orderRepository.save(order);
    }

    // Confirm Order

    @Transactional
    public RestaurantOrder confirmOrder(Long orderId) {

        RestaurantOrder order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + orderId));

        // Only PLACED orders can be confirmed
        if (order.getStatus() != OrderStatus.PLACED) {
            throw new RuntimeException(
                    "Only PLACED orders can be confirmed");
        }

        // Check and deduct inventory for every item
        for (OrderItem orderItem : order.getItems()) {

            Inventory inventory = inventoryRepository
                    .findFirstByVegetableIdAndInventoryDateOrderByIdAsc(
                            orderItem.getVegetable().getId(),
                            LocalDate.now())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Inventory not found for vegetable: "
                                            + orderItem.getVegetable().getName()));

            // Check sufficient inventory
            if (inventory.getQuantity()
                    .compareTo(orderItem.getQuantity()) < 0) {

                throw new RuntimeException(
                        "Insufficient inventory for vegetable: "
                                + orderItem.getVegetable().getName()
                                + ". Available: "
                                + inventory.getQuantity()
                                + " "
                                + inventory.getUnit());
            }

            // Deduct inventory
            inventory.setQuantity(
                    inventory.getQuantity()
                            .subtract(orderItem.getQuantity())
            );

            inventoryRepository.save(inventory);
        }

        // Change order status
        order.setStatus(OrderStatus.CONFIRMED);

        return orderRepository.save(order);
    }

    // Mark Order Out For Delivery

    @Transactional
    public RestaurantOrder markOutForDelivery(Long orderId) {

        RestaurantOrder order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.CONFIRMED) {
            throw new RuntimeException(
                    "Only CONFIRMED orders can be marked OUT_FOR_DELIVERY");
        }

        order.setStatus(OrderStatus.OUT_FOR_DELIVERY);

        return orderRepository.save(order);
    }

    // Mark Order Delivered

    @Transactional
    public RestaurantOrder markDelivered(Long orderId) {

        RestaurantOrder order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.OUT_FOR_DELIVERY) {
            throw new RuntimeException(
                    "Only OUT_FOR_DELIVERY orders can be marked DELIVERED");
        }

        order.setStatus(OrderStatus.DELIVERED);

        return orderRepository.save(order);
    }

    // Cancel Order

    @Transactional
    public RestaurantOrder cancelOrder(Long orderId) {

        RestaurantOrder order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.PLACED) {
            throw new RuntimeException(
                    "Only PLACED orders can be cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }

    // View All Orders

    public List<RestaurantOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    // View Order By ID

    public RestaurantOrder getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with id: " + id));
    }

    // View Orders By Restaurant

    public List<RestaurantOrder> getOrdersByRestaurant(
            Long restaurantId) {

        return orderRepository.findByRestaurantId(restaurantId);
    }

    // Top 5 Vegetables Ordered In Last 30 Days

    public List<Map<String, Object>> getTop5VegetablesLast30Days() {

        LocalDateTime fromDate = LocalDateTime.now().minusDays(30);

        List<Object[]> results =
                orderItemRepository.findTopVegetablesLast30Days(
                        fromDate,
                        OrderStatus.CANCELLED,
                        PageRequest.of(0, 5)
                );

        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {

            Map<String, Object> data = new HashMap<>();

            data.put("vegetableId", row[0]);
            data.put("vegetableName", row[1]);
            data.put("totalQuantity", row[2]);

            response.add(data);
        }

        return response;
    }
}