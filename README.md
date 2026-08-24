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

```text
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
PostgreSQL Database
### Controller Layer

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

## 5. Project Structure

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
    └── VendorService.java
---

## 6. Database

The application uses PostgreSQL as the database.

JPA/Hibernate is used for ORM and entity relationship management.

### Main Entities

- Vendor
- Restaurant
- Vegetable
- Inventory
- RestaurantOrder
- OrderItem

---

## 7. Vendor Management

The backend supports:

- Add vendor
- View all vendors
- View vendor by ID
- Update vendor
- Activate vendor
- Deactivate vendor

Inactive vendors cannot be used for inventory operations.

---

## 8. Restaurant Management

The backend supports:

- Add restaurant
- View all restaurants
- View restaurant by ID
- Update restaurant
- Activate restaurant
- Deactivate restaurant

Inactive restaurants cannot place orders.

---

## 9. Daily Inventory Management

Vendors can add daily vegetable inventory with:

- Vegetable
- Available quantity
- Unit
- Price
- Inventory date

The system prevents duplicate inventory records for the same vegetable on the same day.

Inventory can also be viewed and updated.

---

## 10. Restaurant Order Management

Restaurants can place orders containing multiple vegetables.

The order request contains:

- Vegetable name
- Quantity
- Unit

The final order amount is calculated by the backend using the inventory price.

The client does not provide the final order amount.

### Order Calculation

```text
Item Subtotal = Quantity × Price

Total Amount = Sum of all item subtotals
---

## 11. Order Lifecycle

Orders follow the following lifecycle:

```text
PLACED
   ↓
CONFIRMED
   ↓
OUT_FOR_DELIVERY
   ↓
DELIVERED


```markdown
Cancellation is also supported according to the implemented business rules.

Invalid status transitions are rejected.

---

## 12. Inventory Business Logic

When an order is confirmed:

1. The system checks inventory availability.
2. If sufficient inventory is available, the order is confirmed.
3. The ordered quantity is automatically deducted from inventory.
4. If sufficient inventory is not available, the operation is rejected.

### Example

```text
Available Inventory = 10 kg
Ordered Quantity    = 2 kg

After Confirmation:
Available Inventory = 8 kg
---

## 13. Validation & Exception Handling

The application uses centralized exception handling.

The following cases are handled:

- Duplicate vendor records
- Duplicate restaurant records
- Missing vendor
- Missing restaurant
- Missing inventory
- Missing vegetable
- Missing order
- Insufficient inventory
- Invalid order status transition
- Invalid request
- Validation errors
- Unexpected server errors

---

## 14. Top 5 Vegetables API

The application provides an API to identify the top 5 vegetables based on quantity ordered during the last 30 days.

### Endpoint

```text
GET /api/orders/top-vegetables
The response contains:

- Vegetable name
- Vegetable ID
- Total quantity ordered

---

## 15. API Documentation

Complete API testing documentation is available in:

```text
API_TESTING.md

Phir ye paste karo:

```markdown
The documentation contains:

- Request methods
- API URLs
- Request bodies
- Expected responses
- Business logic testing
- Exception handling testing
- API summary

---

## 16. API Summary

| Module | Number of APIs |
|---|---:|
| Vendor | 6 |
| Restaurant | 6 |
| Inventory | 4 |
| Orders | 9 |
| **Total** | **25** |
---

## 17. How to Run the Application

### Prerequisites

Make sure the following are installed:

- Java 17
- Maven
- PostgreSQL

### Database Configuration

Configure the PostgreSQL database details in:

```text
src/main/resources/application.properties
Set the required database URL, username and password.

### Run Using Maven

```bash
mvn spring-boot:run
http://localhost:8081
---

## 18. Postman Testing

The APIs were tested using Postman with the running Spring Boot application.

The project includes API testing documentation in:

```text
API_TESTING.md
A Postman collection can also be imported to test the APIs.

---

## 19. API Testing Summary

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
---

## 20. Application URL

```text
http://localhost:8081
```

---

## 21. Author

Gaurav Shukla
