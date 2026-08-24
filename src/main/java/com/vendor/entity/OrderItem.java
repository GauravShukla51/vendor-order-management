package com.vendor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private RestaurantOrder order;

    @ManyToOne
    @JoinColumn(name = "vegetable_id", nullable = false)
    private Vegetable vegetable;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal subtotal;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RestaurantOrder getOrder() {
        return order;
    }

    public void setOrder(RestaurantOrder order) {
        this.order = order;
    }

    public Vegetable getVegetable() {
        return vegetable;
    }

    public void setVegetable(Vegetable vegetable) {
        this.vegetable = vegetable;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}