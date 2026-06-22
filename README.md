# 🍔 Food Delivery App — Backend API

A production-ready **RESTful backend** for a food delivery platform built with **Spring Boot 3**, featuring complete restaurant management, cart system, and order lifecycle management.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Data Models & Relationships](#data-models--relationships)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Request & Response Examples](#request--response-examples)
- [Error Handling](#error-handling)
- [Order Status Flow](#order-status-flow)
- [Future Enhancements](#future-enhancements)

---

## Overview

This is the **backend API** for a food delivery application. It handles:

- User registration and management
- Restaurant and food item catalog
- Shopping cart with persistent state
- Order placement, tracking, and status management
- Full error handling with standardized responses

> **Note:** Authentication and security (JWT) are planned for a future release. Currently all endpoints are open.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL 8 |
| Build Tool | Maven |
| API Style | RESTful JSON |
| Validation | Jakarta Bean Validation |
| Dev Tools | Spring Boot DevTools, Lombok |

---

## Project Structure

```
food-delivery/
├── pom.xml
├── src/
│   └── main/
│       ├── resources/
│       │   └── application.yml
│       └── java/com/fooddelivery/
│           ├── FoodDeliveryApplication.java
│           │
│           ├── model/
│           │   ├── enums/
│           │   │   └── OrderStatus.java
│           │   ├── User.java
│           │   ├── Restaurant.java
│           │   ├── FoodItem.java
│           │   ├── Cart.java
│           │   ├── CartItem.java
│           │   ├── Order.java
│           │   └── OrderItem.java
│           │
│           ├── repository/
│           │   ├── UserRepository.java
│           │   ├── RestaurantRepository.java
│           │   ├── FoodItemRepository.java
│           │   ├── CartRepository.java
│           │   ├── CartItemRepository.java
│           │   └── OrderRepository.java
│           │
│           ├── dto/
│           │   ├── request/
│           │   │   ├── UserRequest.java
│           │   │   ├── RestaurantRequest.java
│           │   │   ├── FoodItemRequest.java
│           │   │   ├── AddToCartRequest.java
│           │   │   ├── UpdateCartItemRequest.java
│           │   │   ├── PlaceOrderRequest.java
│           │   │   └── UpdateOrderStatusRequest.java
│           │   └── response/
│           │       ├── ApiResponse.java
│           │       ├── UserResponse.java
│           │       ├── RestaurantResponse.java
│           │       ├── FoodItemResponse.java
│           │       ├── CartItemResponse.java
│           │       ├── CartResponse.java
│           │       ├── OrderItemResponse.java
│           │       └── OrderResponse.java
│           │
│           ├── service/
│           │   ├── UserService.java
│           │   ├── RestaurantService.java
│           │   ├── FoodItemService.java
│           │   ├── CartService.java
│           │   └── OrderService.java
│           │
│           ├── controller/
│           │   ├── UserController.java
│           │   ├── RestaurantController.java
│           │   ├── FoodItemController.java
│           │   ├── CartController.java
│           │   └── OrderController.java
│           │
│           └── exception/
│               ├── ResourceNotFoundException.java
│               ├── BadRequestException.java
│               └── GlobalExceptionHandler.java
```

---

## Data Models & Relationships

```
User ──────────────── Cart (OneToOne)
 │                     │
 │                     └── CartItem (OneToMany)
 │                              │
 │                              └── FoodItem (ManyToOne)
 │
 └── Order (OneToMany)
        │
        ├── Restaurant (ManyToOne)
        │
        └── OrderItem (OneToMany)
                 │
                 └── FoodItem (ManyToOne)  ← price snapshot at order time

Restaurant ─── FoodItem (OneToMany)
```

### Entity Summary

| Entity | Key Fields |
|---|---|
| `User` | id, name, email, phone, address |
| `Restaurant` | id, name, address, cuisine, rating |
| `FoodItem` | id, name, description, price, available, restaurant |
| `Cart` | id, user, cartItems, totalAmount |
| `CartItem` | id, cart, foodItem, quantity, subtotal |
| `Order` | id, user, restaurant, orderItems, totalAmount, status, deliveryAddress |
| `OrderItem` | id, order, foodItem, quantity, priceAtOrderTime |

### OrderStatus Enum

```
PENDING → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED
                                                   ↘
                                                 CANCELLED
```

---

## API Endpoints

Base URL: `http://localhost:8080/api`

All responses follow a standard wrapper:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { },
  "timestamp": "2025-01-01T10:00:00"
}
```

---

### 👤 Users

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/users` | Create a new user |
| `GET` | `/users` | Get all users |
| `GET` | `/users/{id}` | Get user by ID |
| `PUT` | `/users/{id}` | Update user |
| `DELETE` | `/users/{id}` | Delete user |

---

### 🏪 Restaurants

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/restaurants` | Add a restaurant |
| `GET` | `/restaurants` | Get all restaurants |
| `GET` | `/restaurants/{id}` | Get restaurant by ID |
| `PUT` | `/restaurants/{id}` | Update restaurant |
| `DELETE` | `/restaurants/{id}` | Delete restaurant |

---

### 🍕 Food Items

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/food-items` | Add a food item |
| `GET` | `/food-items` | Get all food items |
| `GET` | `/food-items/{id}` | Get food item by ID |
| `GET` | `/food-items/restaurant/{restaurantId}` | Get items by restaurant |
| `PUT` | `/food-items/{id}` | Update food item |
| `DELETE` | `/food-items/{id}` | Delete food item |

---

### 🛒 Cart

Cart is scoped per user: `/api/users/{userId}/cart`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/users/{userId}/cart` | Get user's cart (auto-creates if none) |
| `POST` | `/users/{userId}/cart/items` | Add item to cart |
| `PUT` | `/users/{userId}/cart/items/{itemId}` | Update item quantity |
| `DELETE` | `/users/{userId}/cart/items/{itemId}` | Remove item from cart |
| `DELETE` | `/users/{userId}/cart` | Clear entire cart |

---

### 📦 Orders

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/users/{userId}/orders` | Place order from cart |
| `GET` | `/users/{userId}/orders` | Get all orders for user |
| `GET` | `/orders/{orderId}` | Get order by ID |
| `PUT` | `/orders/{orderId}/status` | Update order status |
| `PUT` | `/orders/{orderId}/cancel` | Cancel an order |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Clone the repository

```bash
git clone https://github.com/your-username/food-delivery.git
cd food-delivery
```

### 2. Create the database

```sql
CREATE DATABASE food_delivery;
```

### 3. Configure `application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/food_delivery
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The API is now available at `http://localhost:8080/api`

---

## Configuration

| Property | Default | Description |
|---|---|---|
| `server.port` | `8080` | Server port |
| `spring.jpa.ddl-auto` | `update` | Schema strategy (`create` / `update` / `validate`) |
| `spring.jpa.show-sql` | `true` | Log SQL queries |

---

## Request & Response Examples

### Create a User

**Request**
```http
POST /api/users
Content-Type: application/json

{
  "name": "Ravi Kumar",
  "email": "ravi@example.com",
  "phone": "9876543210",
  "address": "123 MG Road, Hyderabad"
}
```

**Response**
```json
{
  "success": true,
  "message": "User created successfully",
  "data": {
    "id": 1,
    "name": "Ravi Kumar",
    "email": "ravi@example.com",
    "phone": "9876543210",
    "address": "123 MG Road, Hyderabad"
  },
  "timestamp": "2025-01-01T10:00:00"
}
```

---

### Add Item to Cart

**Request**
```http
POST /api/users/1/cart/items
Content-Type: application/json

{
  "foodItemId": 3,
  "quantity": 2
}
```

**Response**
```json
{
  "success": true,
  "message": "Item added to cart",
  "data": {
    "id": 10,
    "totalAmount": 299.98,
    "cartItems": [
      {
        "id": 5,
        "foodItemId": 3,
        "foodItemName": "Paneer Butter Masala",
        "quantity": 2,
        "price": 149.99,
        "subtotal": 299.98
      }
    ]
  },
  "timestamp": "2025-01-01T10:05:00"
}
```

---

### Place an Order

**Request**
```http
POST /api/users/1/orders
Content-Type: application/json

{
  "deliveryAddress": "123 MG Road, Hyderabad"
}
```

**Response**
```json
{
  "success": true,
  "message": "Order placed successfully",
  "data": {
    "id": 42,
    "userId": 1,
    "restaurantName": "Spice Garden",
    "status": "PENDING",
    "totalAmount": 299.98,
    "deliveryAddress": "123 MG Road, Hyderabad",
    "orderItems": [
      {
        "foodItemName": "Paneer Butter Masala",
        "quantity": 2,
        "priceAtOrderTime": 149.99,
        "subtotal": 299.98
      }
    ],
    "createdAt": "2025-01-01T10:10:00"
  },
  "timestamp": "2025-01-01T10:10:00"
}
```

---

### Update Order Status

**Request**
```http
PUT /api/orders/42/status
Content-Type: application/json

{
  "status": "CONFIRMED"
}
```

---

## Error Handling

All errors are handled globally via `@RestControllerAdvice` and return a consistent structure:

```json
{
  "success": false,
  "message": "Food item not found with id: 99",
  "data": null,
  "timestamp": "2025-01-01T10:15:00"
}
```

| Exception | HTTP Status |
|---|---|
| `ResourceNotFoundException` | `404 Not Found` |
| `BadRequestException` | `400 Bad Request` |
| Validation errors | `400 Bad Request` |
| Unhandled exceptions | `500 Internal Server Error` |

---

## Order Status Flow

```
[Place Order]
     │
     ▼
  PENDING ──────────────────────► CANCELLED
     │
     ▼
CONFIRMED ────────────────────── CANCELLED
     │
     ▼
PREPARING
     │
     ▼
OUT_FOR_DELIVERY
     │
     ▼
 DELIVERED
```

**Rules enforced by the service layer:**
- Orders in `DELIVERED` status cannot be cancelled
- Status can only move forward (no going back from `PREPARING` to `PENDING`)
- Cancellation is only allowed before `OUT_FOR_DELIVERY`

---

## Future Enhancements

- [ ] JWT-based authentication & authorization
- [ ] Role-based access control (CUSTOMER / RESTAURANT_OWNER / ADMIN)
- [ ] Payment gateway integration (Razorpay / Stripe)
- [ ] Real-time order tracking with WebSocket
- [ ] Email/SMS notifications on order status change
- [ ] Restaurant search by location / cuisine
- [ ] Rating & review system
- [ ] Discount & coupon system
- [ ] Swagger / OpenAPI documentation
- [ ] Docker + Docker Compose setup
- [ ] CI/CD pipeline with GitHub Actions

---

## Author

**Your Name**
- GitHub: [@your-username](https://github.com/your-username)
- LinkedIn: [your-profile](https://linkedin.com/in/your-profile)

---

## License

This project is licensed under the MIT License.
