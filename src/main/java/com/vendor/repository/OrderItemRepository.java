package com.vendor.repository;

import com.vendor.entity.OrderItem;
import com.vendor.entity.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    @Query("""
        SELECT oi.vegetable.id,
               oi.vegetable.name,
               SUM(oi.quantity)
        FROM OrderItem oi
        WHERE oi.order.createdAt >= :fromDate
          AND oi.order.status <> :cancelledStatus
        GROUP BY oi.vegetable.id, oi.vegetable.name
        ORDER BY SUM(oi.quantity) DESC
        """)
    List<Object[]> findTopVegetablesLast30Days(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("cancelledStatus") OrderStatus cancelledStatus,
            Pageable pageable
    );
}