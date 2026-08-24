# API Testing Documentation

## Base URL

http://localhost:8081

The APIs were tested using Postman Lightweight API Client.

---

# 1. Vendor APIs

## 1.1 Create Vendor

**Method:** POST

**URL:**

http://localhost:8081/api/vendors

**Request Body:**

```json
{
  "name": "Test Supplier",
  "phone": "9876543211",
  "email": "test.supplier@gmail.com",
  "address": "Lucknow, Uttar Pradesh"
}
```

**Expected Response:** `201 Created`

---

## 1.2 Get All Vendors

**Method:** GET

**URL:**

http://localhost:8081/api/vendors

**Expected Response:** `200 OK`

---

## 1.3 Get Vendor By ID

**Method:** GET

**URL:**

http://localhost:8081/api/vendors/1

**Expected Response:** `200 OK`

---

## 1.4 Update Vendor

**Method:** PUT

**URL:**

http://localhost:8081/api/vendors/1

**Request Body:**

```json
{
  "name": "Gaurav Updated Supplier",
  "phone": "9876543211",
  "email": "test.supplier@gmail.com",
  "address": "Lucknow, Uttar Pradesh"
}
```

**Expected Response:** `200 OK`

---

## 1.5 Activate Vendor

**Method:** PATCH

**URL:**

http://localhost:8081/api/vendors/1/activate

**Expected Response:** `200 OK`

---

## 1.6 Deactivate Vendor

**Method:** PATCH

**URL:**

http://localhost:8081/api/vendors/1/deactivate

**Expected Response:** `200 OK`

---

# 2. Restaurant APIs

## 2.1 Create Restaurant

**Method:** POST

**URL:**

http://localhost:8081/api/restaurants

**Request Body:**

```json
{
  "name": "Test Restaurant",
  "phone": "9123456781",
  "email": "test.restaurant@gmail.com",
  "address": "Lucknow, Uttar Pradesh"
}
```

**Expected Response:** `201 Created`

---

## 2.2 Get All Restaurants

**Method:** GET

**URL:**

http://localhost:8081/api/restaurants

**Expected Response:** `200 OK`

---

## 2.3 Get Restaurant By ID

**Method:** GET

**URL:**

http://localhost:8081/api/restaurants/1

**Expected Response:** `200 OK`

---

## 2.4 Update Restaurant

**Method:** PUT

**URL:**

http://localhost:8081/api/restaurants/1

**Request Body:**

```json
{
  "name": "Gaurav Updated Restaurant",
  "phone": "9123456780",
  "email": "gaurav.restaurant@gmail.com",
  "address": "Lucknow, Uttar Pradesh"
}
```

**Expected Response:** `200 OK`

---

## 2.5 Activate Restaurant

**Method:** PATCH

**URL:**

http://localhost:8081/api/restaurants/1/activate

**Expected Response:** `200 OK`

---

## 2.6 Deactivate Restaurant

**Method:** PATCH

**URL:**

http://localhost:8081/api/restaurants/1/deactivate

**Expected Response:** `200 OK`

---

# 3. Inventory APIs

## 3.1 Add Inventory

**Method:** POST

**URL:**

http://localhost:8081/api/inventory/vendor/1?vegetableName=Tomato

**Request Body:**

```json
{
  "quantity": 50,
  "unit": "kg",
  "price": 45
}
```

**Expected Response:** `201 Created`

---

## 3.2 Get Today's Inventory

**Method:** GET

**URL:**

http://localhost:8081/api/inventory/today

**Expected Response:** `200 OK`

---

## 3.3 Get Inventory By ID

**Method:** GET

**URL:**

http://localhost:8081/api/inventory/1

**Expected Response:** `200 OK`

---

## 3.4 Update Inventory

**Method:** PUT

**URL:**

http://localhost:8081/api/inventory/1

**Request Body:**

```json
{
  "quantity": 100,
  "unit": "kg",
  "price": 40
}
```

**Expected Response:** `200 OK`

---

# 4. Restaurant Order APIs

## 4.1 Create Order

**Method:** POST

**URL:**

http://localhost:8081/api/orders/restaurant/1

**Request Body:**

```json
{
  "items": [
    {
      "vegetableName": "Tomato",
      "quantity": 2,
      "unit": "kg"
    },
    {
      "vegetableName": "Potato",
      "quantity": 3,
      "unit": "kg"
    }
  ]
}
```

**Expected Response:** `201 Created`

The order status should initially be:

```text
PLACED
```

The total amount is calculated by the backend based on vegetable price and ordered quantity.

---

## 4.2 Confirm Order

**Method:** PATCH

**URL:**

http://localhost:8081/api/orders/1/confirm

**Expected Response:** `200 OK`

When the order is confirmed, the required inventory is automatically deducted.

---

## 4.3 Mark Order Out For Delivery

**Method:** PATCH

**URL:**

http://localhost:8081/api/orders/1/out-for-delivery

**Expected Response:** `200 OK`

This operation is allowed only for `CONFIRMED` orders.

---

## 4.4 Mark Order Delivered

**Method:** PATCH

**URL:**

http://localhost:8081/api/orders/1/delivered

**Expected Response:** `200 OK`

This operation is allowed only for `OUT_FOR_DELIVERY` orders.

---

## 4.5 Cancel Order

**Method:** PATCH

**URL:**

http://localhost:8081/api/orders/1/cancel

**Expected Response:** `200 OK`

The order is cancelled according to the implemented business rules.

---

## 4.6 Get All Orders

**Method:** GET

**URL:**

http://localhost:8081/api/orders

**Expected Response:** `200 OK`

---

## 4.7 Get Top 5 Vegetables Ordered In Last 30 Days

**Method:** GET

**URL:**

http://localhost:8081/api/orders/top-vegetables

**Expected Response:** `200 OK`

**Example Response:**

```json
[
  {
    "vegetableName": "Tomato",
    "totalQuantity": 15.00,
    "vegetableId": 1
  },
  {
    "vegetableName": "Potato",
    "totalQuantity": 3.00,
    "vegetableId": 2
  }
]
```

This API identifies the top vegetables based on quantity ordered during the last 30 days.

---

## 4.8 Get Order By ID

**Method:** GET

**URL:**

http://localhost:8081/api/orders/1

**Expected Response:** `200 OK`

---

## 4.9 Get Orders By Restaurant

**Method:** GET

**URL:**

http://localhost:8081/api/orders/restaurant/1

**Expected Response:** `200 OK`

---

# 5. Business Logic Testing

## 5.1 Insufficient Inventory

An order should not be processed when sufficient inventory is unavailable.

Example:

```text
Available Tomato inventory = 10 kg
Requested quantity = 15 kg
```

The API should reject the order.

**Expected Response:** `400 Bad Request`

---

## 5.2 No Inventory Available

If no inventory exists for the requested vegetable, the order should be rejected.

**Example Response:**

```json
{
  "error": "Bad Request",
  "message": "No inventory available for vegetable: Tomato",
  "status": 400
}
```

---

## 5.3 Invalid Order Status Transition

Orders follow the lifecycle:

```text
PLACED
   ↓
CONFIRMED
   ↓
OUT_FOR_DELIVERY
   ↓
DELIVERED
```

An invalid status transition should return `400 Bad Request`.

**Example:**

Trying to mark a `PLACED` order as `OUT_FOR_DELIVERY`.

**Example Response:**

```json
{
  "error": "Bad Request",
  "message": "Only CONFIRMED orders can be marked OUT_FOR_DELIVERY",
  "status": 400
}
```

---

## 5.4 Automatic Inventory Deduction

When an order is confirmed, the ordered quantity is automatically deducted from available inventory.

Example:

```text
Available inventory = 10 kg
Ordered quantity    = 2 kg

After confirmation:
Available inventory = 8 kg
```

---

## 5.5 Automatic Order Calculation

The client does not provide the final order amount.

The backend calculates the amount using:

```text
Subtotal = Quantity × Price
```

and:

```text
Total Amount = Sum of all item subtotals
```

Example:

```text
Potato
Quantity = 1 kg
Price = ₹30

Total = ₹30
```

---

# 6. Exception Handling Testing

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

# 7. API Summary

| Module | Number of APIs |
|---|---:|
| Vendor | 6 |
| Restaurant | 6 |
| Inventory | 4 |
| Orders | 9 |
| **Total** | **25** |

---

# 8. Application URL

The application runs on:

http://localhost:8081

The backend uses PostgreSQL with Spring Boot, Spring Data JPA and Hibernate.