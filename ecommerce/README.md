# E-Commerce Management System - Spring Boot Backend

## 🛠️ Setup Steps (IntelliJ IDEA)

1. **Open Project**: IntelliJ → Open → select the `ecommerce` folder
2. **Wait for Maven** to download dependencies (needs internet first time)
3. **MySQL**: Make sure MySQL server is running
4. **Edit password**: Open `src/main/resources/application.properties` → change `spring.datasource.password` to your MySQL root password
5. **Run**: Right-click `EcommerceApplication.java` → Run
6. Server starts at `http://localhost:8080`

Database `ecommerce_db` and all tables auto-create (no manual SQL needed).

## 📋 All Features / Endpoints

| # | Feature | Method | Endpoint |
|---|---------|--------|----------|
| 1 | Register | POST | /api/auth/register |
| 2 | Login (JWT) | POST | /api/auth/login |
| 3 | Add Product (Admin) | POST | /api/products |
| 4 | Update Product (Admin) | PUT | /api/products/{id} |
| 5 | Delete Product (Admin) | DELETE | /api/products/{id} |
| 6 | Get Product | GET | /api/products/{id} |
| 7 | Get All Products | GET | /api/products |
| 8 | Search Product | GET | /api/products/search?keyword= |
| 9 | Filter Product (price) | GET | /api/products/filter/price?min=&max= |
| 9 | Filter Product (brand) | GET | /api/products/filter/brand?brand= |
| 9 | Filter Product (category) | GET | /api/products/category/{categoryId} |
| 10 | Add Category (Admin) | POST | /api/categories |
| 11 | Update Category (Admin) | PUT | /api/categories/{id} |
| 12 | Delete Category (Admin) | DELETE | /api/categories/{id} |
| 13 | Add Review | POST | /api/reviews/product/{productId} |
| - | Get Reviews | GET | /api/reviews/product/{productId} |
| 14 | Add to Cart | POST | /api/cart/add |
| 15 | Update Cart | PUT | /api/cart/update |
| - | Remove from Cart | DELETE | /api/cart/remove/{productId} |
| - | Get Cart | GET | /api/cart |
| 16 | Place Order | POST | /api/orders/place |
| 17 | Cancel Order | PUT | /api/orders/cancel/{orderId} |
| 18 | Order History | GET | /api/orders/history |
| - | All Orders (Admin) | GET | /api/orders/all |
| - | Update Order Status (Admin) | PUT | /api/orders/{id}/status?status= |
| 19 | Low Stock Alert (Admin) | GET | /api/products/low-stock |

## 🔐 Auth Rules
- Register/Login: Public
- View products/categories (GET): Public
- Cart, Orders, Reviews: Need login (Bearer token)
- Create/Update/Delete Product/Category, Low Stock, All Orders: Need ADMIN role

To make a user ADMIN: register normally, then in MySQL run:
```sql
UPDATE users SET role='ADMIN' WHERE email='your_admin_email@gmail.com';
```
