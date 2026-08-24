package com.vendor.repository;

import com.vendor.entity.OrderStatus;
import com.vendor.entity.RestaurantOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantOrderRepository
        extends JpaRepository<RestaurantOrder, Long> {

    List<RestaurantOrder> findByRestaurantId(Long restaurantId);

    List<RestaurantOrder> findByStatus(OrderStatus status);

    // Top 5 vegetables by quantity ordered in last 30 days
    @Query(value = """
            SELECT 
                v.name AS vegetableName,
                SUM(oi.quantity) AS totalQuantity
            FROM order_items oi
            JOIN vegetables v ON oi.vegetable_id = v.id
            JOIN orders o ON oi.order_id = o.id
            WHERE o.created_at >= CURRENT_TIMESTAMP - INTERVAL '30 days'
              AND o.status <> 'CANCELLED'
            GROUP BY v.id, v.name
            ORDER BY SUM(oi.quantity) DESC
            LIMIT 5
            """, nativeQuery = true)
    List<Object[]> findTop5VegetablesLast30Days();
}