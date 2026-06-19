package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // PLACE ORDER - POST /api/orders/place
    // body: { "shippingAddress": "123 Street, Chennai", "paymentMethod": "COD" }
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, String> body, Authentication authentication) {
        Order order = orderService.placeOrder(
                authentication.getName(),
                body.get("shippingAddress"),
                body.get("paymentMethod")
        );
        return ResponseEntity.ok(Map.of("message", "Order placed successfully!", "order", order));
    }

    // CANCEL ORDER - PUT /api/orders/cancel/{orderId}
    // body: { "reason": "Changed my mind" }
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId,
                                          @RequestBody(required = false) Map<String, String> body,
                                          Authentication authentication) {
        String reason = body != null ? body.get("reason") : null;
        Order order = orderService.cancelOrder(orderId, authentication.getName(), reason);
        return ResponseEntity.ok(Map.of("message", "Order cancelled successfully!", "order", order));
    }

    // ORDER HISTORY - GET /api/orders/history
    @GetMapping("/history")
    public ResponseEntity<List<Order>> orderHistory(Authentication authentication) {
        return ResponseEntity.ok(orderService.getOrderHistory(authentication.getName()));
    }

    // GET SINGLE ORDER - GET /api/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // ALL ORDERS - GET /api/orders/all (Admin only)
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // UPDATE STATUS - PUT /api/orders/{id}/status?status=SHIPPED (Admin only)
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam Order.OrderStatus status) {
        Order order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(Map.of("message", "Order status updated!", "order", order));
    }
}
