# Vendor & Order Management Backend

## 1. Project Overview

This project is a basic backend MVP for managing the flow between vegetable vendors and restaurants.

The application provides:

- Vendor management
- Restaurant management
- Daily vegetable inventory management
- Restaurant order management
- Order lifecycle management
- Inventory availability validation
- Automatic inventory deduction
- Automatic order amount calculation
- Top 5 vegetables ordered in the last 30 days
- Exception handling and validation

---

## 2. Objective

The objective of this application is to manage the complete flow from vegetable vendors to restaurants.

### Main Business Flow

Vendor
↓
Daily Inventory
↓
Restaurant
↓
Order
↓
Order Confirmation
↓
Out for Delivery
↓
Delivery

---

## 3. Tech Stack

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

---

## 4. Application Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL Database### Controller Layer

Handles REST API requests and responses.

### Service Layer

Contains the main business logic such as:

- Order creation
- Order confirmation
- Inventory validation
- Inventory deduction
- Order amount calculation
- Order status transitions
- Vendor management
- Restaurant management
- Inventory management

### Repository Layer

Uses Spring Data JPA for database operations.

### Entity Layer

Contains the JPA entities and their relationships.

### DTO Layer

Contains request objects used for creating orders.

### Exception Layer

Provides centralized exception handling and consistent error responses.

---

# 5. Project Structure

```text
src/main/java/com/vendor

├── controller
│   ├── InventoryController.java
│   ├── RestaurantController.java
│   ├── RestaurantOrderController.java
│   └── VendorController.java
│
├── dto
│   ├── CreateOrderRequest.java
│   └── OrderItemRequest.java
│
├── entity
│   ├── Inventory.java
│   ├── OrderItem.java
│   ├── OrderStatus.java
│   ├── Restaurant.java
│   ├── RestaurantOrder.java
│   ├── Vegetable.java
│   └── Vendor.java
│
├── exception
│   ├── DuplicateResourceException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
├── repository
│   ├── InventoryRepository.java
│   ├── OrderItemRepository.java
│   ├── RestaurantOrderRepository.java
│   ├── RestaurantRepository.java
│   ├── VegetableRepository.java
│   └── VendorRepository.java
│
└── service
    ├── InventoryService.java
    ├── RestaurantOrderService.java
    ├── RestaurantService.java
    └── VendorService.java### Controller Layer

Handles REST API requests and responses.

### Service Layer

Contains the main business logic such as:

- Order creation
- Order confirmation
- Inventory validation
- Inventory deduction
- Order amount calculation
- Order status transitions
- Vendor management
- Restaurant management
- Inventory management

### Repository Layer

Uses Spring Data JPA for database operations.

### Entity Layer

Contains the JPA entities and their relationships.

### DTO Layer

Contains request objects used for creating orders.

### Exception Layer

Provides centralized exception handling and consistent error responses.

---

# 5. Project Structure

```text
src/main/java/com/vendor

├── controller
│   ├── InventoryController.java
│   ├── RestaurantController.java
│   ├── RestaurantOrderController.java
│   └── VendorController.java
│
├── dto
│   ├── CreateOrderRequest.java
│   └── OrderItemRequest.java
│
├── entity
│   ├── Inventory.java
│   ├── OrderItem.java
│   ├── OrderStatus.java
│   ├── Restaurant.java
│   ├── RestaurantOrder.java
│   ├── Vegetable.java
│   └── Vendor.java
│
├── exception
│   ├── DuplicateResourceException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
├── repository
│   ├── InventoryRepository.java
│   ├── OrderItemRepository.java
│   ├── RestaurantOrderRepository.java
│   ├── RestaurantRepository.java
│   ├── VegetableRepository.java
│   └── VendorRepository.java
│
└── service
    ├── InventoryService.java
    ├── RestaurantOrderService.java
    ├── RestaurantService.java
    └── VendorService.java# 19. API Testing Summary

All major APIs were tested using Postman with the running Spring Boot application.

### Vendor APIs
- Create Vendor
- Get All Vendors
- Get Vendor By ID
- Update Vendor
- Activate Vendor
- Deactivate Vendor

### Restaurant APIs
- Create Restaurant
- Get All Restaurants
- Get Restaurant By ID
- Update Restaurant
- Activate Restaurant
- Deactivate Restaurant

### Inventory APIs
- Add Inventory
- Get Today's Inventory
- Get Inventory By ID
- Update Inventory

### Order APIs
- Create Order
- Get All Orders
- Get Order By ID
- Get Orders By Restaurant
- Confirm Order
- Mark Order Out For Delivery
- Mark Order Delivered
- Cancel Order
- Get Top 5 Vegetables Ordered In Last 30 Days

### Business Logic Tested

- Order total is calculated by the backend.
- Orders cannot be confirmed when sufficient inventory is unavailable.
- Inventory is deducted automatically when an order is confirmed.
- Invalid order status transitions are rejected.
- Duplicate vendor/restaurant records are handled.
- Missing resources are handled with appropriate error responses.
- Order cancellation is supported.