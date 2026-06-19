package com.ecommerce.service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.*;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final CartService cartService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepo, UserRepository userRepo,
                         CartService cartService, ProductService productService) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.cartService = cartService;
        this.productService = productService;
    }

    // PLACE ORDER - from cart
    @Transactional
    public Order placeOrder(String userEmail, String shippingAddress, String paymentMethod) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartService.getCart(userEmail);
        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty! Add products before placing order.");
        }
        if (shippingAddress == null || shippingAddress.isBlank()) {
            throw new BadRequestException("Shipping address required!");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            // Reduce stock - throws if insufficient
            productService.reduceStock(product.getId(), cartItem.getQuantity());

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .productName(product.getName())
                    .priceAtOrder(product.getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();
            orderItems.add(orderItem);
            total += product.getPrice() * cartItem.getQuantity();
        }

        Order order = Order.builder()
                .user(user)
                .totalAmount(total)
                .shippingAddress(shippingAddress)
                .paymentMethod(paymentMethod != null ? paymentMethod : "COD")
                .status(Order.OrderStatus.PENDING)
                .build();

        // link items to order
        orderItems.forEach(item -> item.setOrder(order));
        order.setItems(orderItems);

        Order saved = orderRepo.save(order);

        // Clear cart after successful order
        cartService.clearCart(cart);

        return saved;
    }

    // CANCEL ORDER
    @Transactional
    public Order cancelOrder(Long orderId, String userEmail, String reason) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        if (!order.getUser().getEmail().equalsIgnoreCase(userEmail)) {
            throw new BadRequestException("You can only cancel your own orders!");
        }

        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new BadRequestException("Cannot cancel a delivered order!");
        }
        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new BadRequestException("Order already cancelled!");
        }

        // Restore stock for each item
        for (OrderItem item : order.getItems()) {
            productService.restoreStock(item.getProduct().getId(), item.getQuantity());
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        order.setCancelReason(reason != null ? reason : "Cancelled by user");

        return orderRepo.save(order);
    }

    // ORDER HISTORY - for logged in user
    public List<Order> getOrderHistory(String userEmail) {
        return orderRepo.findByUserEmailOrderByOrderedAtDesc(userEmail);
    }

    // GET single order
    public Order getOrderById(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    // ALL ORDERS - admin
    public List<Order> getAllOrders() {
        return orderRepo.findAllByOrderByOrderedAtDesc();
    }

    // UPDATE STATUS - admin (confirm/ship/deliver)
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepo.save(order);
    }
}
