# 🛒 E-Commerce Backend REST API

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?style=for-the-badge&logo=mysql)
![JWT](https://img.shields.io/badge/JWT-Authentication-000000?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

A **production-ready, full-featured E-Commerce Backend REST API** built with **Spring Boot 3.x**, **MySQL 8.0+**, and **Spring Security (JWT)**. This comprehensive project implements secure user authentication, complete CRUD operations for products, categories, shopping cart, and orders, along with reviews, ratings, and admin controls.

## 🎯 Project Overview

This backend API serves as the foundation for a modern e-commerce platform. It provides secure, scalable REST endpoints for managing users, products, shopping carts, orders, and reviews. The project is fully tested using **Postman** with automated test scripts and is deployment-ready.

**Perfect for:**
- ✅ Learning Spring Boot development
- ✅ Building production e-commerce systems
- ✅ Resume/Portfolio projects
- ✅ Interview preparation
- ✅ Full-stack integration

---

## ✨ Key Features

### 🔐 Authentication & Authorization
- **JWT Token-Based Authentication** - Secure, stateless user sessions
- **User Registration** - Email validation and password encryption (BCrypt)
- **User Login** - Automatic JWT token generation
- **Role-Based Access Control (RBAC)** - Admin and User roles with permission management
- **Protected API Endpoints** - Authorization checks on sensitive operations
- **Password Security** - BCrypt encryption for secure storage

### 📦 Product Management
- **Complete CRUD Operations** - Create, Read, Update, Delete products
- **Product Search & Filtering** - By category, price range, brand, and stock status
- **Category Management** - Organize products into categories
- **Stock Management** - Track inventory and low-stock alerts
- **Product Images** - URL-based image storage and management
- **Product Details** - Description, pricing, brand, category information

### 🛒 Shopping Cart & Orders
- **Add/Remove/Update Cart Items** - Full cart management functionality
- **Persistent Cart Storage** - Cart data saved in database
- **Order Placement** - Place orders from cart items
- **Order Tracking** - View order history and status
- **Order Status Management** - Pending, Processing, Shipped, Delivered
- **Multiple Items in Order** - Support for bulk orders

### 📊 Admin Dashboard Features
- **Order Management** - View all orders with filtering and sorting
- **Sales Analytics** - Revenue tracking and insights
- **Inventory Management** - Stock levels and low-stock alerts
- **User Management** - View and manage user accounts
- **Review Moderation** - Approve/reject customer reviews
- **Admin Reports** - Sales reports and analytics

### 💬 Reviews & Ratings System
- **Product Reviews** - Users can write detailed reviews
- **Rating System** - 1-5 star rating scale
- **Review Visibility** - Admin moderation before publication
- **Sentiment Analysis** - Track positive/negative feedback
- **Trending Reviews** - Highlight top-rated reviews

### 📈 Advanced Features
- **Pagination & Sorting** - Efficient data retrieval with page controls
- **Input Validation** - Comprehensive request validation
- **Error Handling** - Custom exception handling with meaningful messages
- **Logging** - Application logging for debugging and monitoring
- **CORS Support** - Cross-origin request handling for frontend integration

---

## 🛠️ Technology Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Language** | Java | 17+ |
| **Framework** | Spring Boot | 3.x |
| **Security** | Spring Security + JWT | Latest |
| **Database** | MySQL | 8.0+ |
| **ORM** | Spring Data JPA/Hibernate | Latest |
| **Build Tool** | Maven | 3.6+ |
| **API Testing** | Postman | Latest |
| **Database Tool** | MySQL Workbench | Latest |

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Common Headers
```
Content-Type: application/json
Authorization: Bearer {jwt_token}
```

---

## 🧪 Testing with Postman

### Import Collection

1. **Download** `Ecommerce-Backend.postman_collection.json` from this repository
2. Open **Postman** → **File** → **Import**
3. Select the JSON collection file
4. Collection loads with all pre-configured requests

### Set Environment Variables

1. Create new environment: **Environments** → **Create**
2. Add variables:
   - `base_url` = `http://localhost:8080/api`
   - `token` = (auto-populated after login)
3. Select environment before testing

### Run Tests

1. Select collection → **Run**
2. Execute requests in sequence
3. View responses and test results
4. Automated scripts validate responses

---

## 📁 Project Structure

```
ecommerce-backend-spring-boot/
├── src/
│   ├── main/
│   │   ├── java/com/ecommerce/
│   │   │   ├── controller/          # REST API Controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── CartController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   └── ReviewController.java
│   │   │   │
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── CartService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   └── ReviewService.java
│   │   │   │
│   │   │   ├── model/               # Entity Classes
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Category.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── CartItem.java
│   │   │   │   ├── Order.java
│   │   │   │   ├── OrderItem.java
│   │   │   │   └── Review.java
│   │   │   │
│   │   │   ├── repository/          # Data Access Layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── CartRepository.java
│   │   │   │   └── OrderRepository.java
│   │   │   │
│   │   │   ├── config/              # Configuration
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── JwtConfig.java
│   │   │   │   └── CorsConfig.java
│   │   │   │
│   │   │   ├── exception/           # Exception Handling
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── CustomException.java
│   │   │   │
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── util/                # Utility Classes
│   │   │   └── EcommerceApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── logback.xml
│   │
│   └── test/                        # Unit Tests
│
├── pom.xml                          # Maven Configuration
├── ecommerce_db.sql                 # MySQL Schema
├── Ecommerce-Backend.postman_collection.json
├── README.md
├── .gitignore
└── LICENSE
```

---

## 🔒 Security Features

### Authentication & Authorization
- ✅ **JWT Tokens** - Secure, time-limited access tokens
- ✅ **Password Encryption** - BCrypt hashing for passwords
- ✅ **Role-Based Access** - Admin and User role management
- ✅ **Token Validation** - All protected endpoints require valid JWT

### Data Protection
- ✅ **Input Validation** - Comprehensive request validation
- ✅ **SQL Injection Prevention** - Parameterized queries via JPA
- ✅ **CORS Security** - Controlled cross-origin requests
- ✅ **Exception Handling** - No sensitive data in error messages

### Production Ready
- ✅ **HTTPS Compatible** - Ready for SSL/TLS deployment
- ✅ **Environment Variables** - Secrets not hardcoded
- ✅ **Logging** - Security event tracking
- ✅ **Rate Limiting** - Prevents API abuse

---

## 📊 Database Schema

### Key Tables
- **users** - User accounts and credentials
- **categories** - Product categories
- **products** - Product inventory
- **carts** - Shopping carts
- **cart_items** - Items in carts
- **orders** - Customer orders
- **order_items** - Items in orders
- **reviews** - Product reviews
- **ratings** - Product ratings

---

## 🚢 Deployment

### Local Deployment ✅
The project runs on `http://localhost:8080` by default.

### Cloud Deployment (Future)
- AWS Elastic Beanstalk
- Heroku
- Google Cloud Run
- Azure App Service

---

## 🐛 Troubleshooting

### Issue: "MySQL Connection Failed"
**Solution:** 
- Check MySQL is running: `mysql -u root -p`
- Verify credentials in `application.properties`
- Ensure database `ecommerce_db` exists

### Issue: "JWT Token Expired"
**Solution:** Generate new token by logging in again

### Issue: "401 Unauthorized"
**Solution:** 
- Include valid JWT token in Authorization header
- Token format: `Authorization: Bearer {token}`

### Issue: "Cannot find symbol 'Lombok'"
**Solution:** Install Lombok plugin in your IDE or add to pom.xml

---

## 📈 Performance Optimization

- **Database Indexing** - Indexed frequently queried columns
- **Pagination** - Large result sets handled with pagination
- **Lazy Loading** - Optimized entity relationships
- **Caching** - Redis integration ready (future enhancement)

---

## 📚 Learning Resources

- [Spring Boot Official Docs](https://spring.io/projects/spring-boot)
- [Spring Security JWT](https://spring.io/guides/tutorials/spring-security-and-angular-js/)
- [JPA/Hibernate Guide](https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/)
- [MySQL Tutorial](https://dev.mysql.com/doc/)
- [Postman Learning Center](https://learning.postman.com/)

---

## 🎓 Perfect For

✅ **Learning Projects** - Understand Spring Boot architecture  
✅ **Resume/Portfolio** - Showcase full-stack skills  
✅ **Interview Preparation** - Common backend patterns  
✅ **Production Use** - Can be deployed with modifications  
✅ **Team Projects** - Well-documented, easy to extend  

---

## 📝 API Response Format

All responses follow a consistent JSON format:

**Success Response:**
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": { /* response data */ },
  "timestamp": "2026-06-19T12:00:00Z"
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Error description",
  "error": "ERROR_CODE",
  "timestamp": "2026-06-19T12:00:00Z"
}
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/YourFeature`)
3. **Commit** changes (`git commit -m 'Add YourFeature'`)
4. **Push** to branch (`git push origin feature/YourFeature`)
5. **Open** a Pull Request

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Sreenithi**
- 🎓 CSE Graduate, Anna University Chennai (2025)
- 💻 Full-Stack Developer | Backend Specialist
- 📧 Email: sreenithi@example.com
- 🔗 GitHub: [@SREE2005-NITHI](https://github.com/SREE2005-NITHI)
- 💼 LinkedIn: [Sreenithi](https://linkedin.com/in/sreenithi)

---

## 🙏 Support & Feedback

- 💬 Have questions? Open an [Issue](https://github.com/SREE2005-NITHI/ecommerce-backend-spring-boot/issues)
- ⭐ Found it helpful? Please star the repository!
- 🐛 Found a bug? Please report it with details
- 💡 Have suggestions? We'd love to hear them!

---

## 📊 Project Statistics

- **Lines of Code:** 2000+
- **API Endpoints:** 20+
- **Database Tables:** 8
- **Test Cases:** 15+
- **Development Time:** Ongoing

---

**Last Updated:** June 2026  
**Version:** 1.0.0  
**Status:** ✅ Production Ready

---

## 🎯 Next Steps

1. ✅ Clone the repository
2. ✅ Set up MySQL database
3. ✅ Configure application.properties
4. ✅ Run `mvn spring-boot:run`
5. ✅ Import Postman collection
6. ✅ Start testing APIs!

---

**Made by Sreenithi**
