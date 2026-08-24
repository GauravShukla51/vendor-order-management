# API Testing Documentation

## Base URL

http://localhost:8081

The APIs were tested using Postman Lightweight API Client.

---

## 1. Vendor APIs

### Create Vendor

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