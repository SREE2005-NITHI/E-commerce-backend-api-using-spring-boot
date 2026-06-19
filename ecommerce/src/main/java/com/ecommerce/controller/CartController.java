package com.ecommerce.controller;

import com.ecommerce.model.Cart;
import com.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ADD TO CART - POST /api/cart/add
    // body: { "productId": 1, "quantity": 2 }
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> body, Authentication authentication) {
        Long productId = Long.valueOf(body.get("productId").toString());
        Integer quantity = Integer.valueOf(body.get("quantity").toString());
        Cart cart = cartService.addToCart(authentication.getName(), productId, quantity);
        return ResponseEntity.ok(Map.of("message", "Added to cart!", "cart", cart));
    }

    // UPDATE CART - PUT /api/cart/update
    // body: { "productId": 1, "quantity": 5 }
    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody Map<String, Object> body, Authentication authentication) {
        Long productId = Long.valueOf(body.get("productId").toString());
        Integer quantity = Integer.valueOf(body.get("quantity").toString());
        Cart cart = cartService.updateCartItem(authentication.getName(), productId, quantity);
        return ResponseEntity.ok(Map.of("message", "Cart updated!", "cart", cart));
    }

    // REMOVE ITEM - DELETE /api/cart/remove/{productId}
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId, Authentication authentication) {
        Cart cart = cartService.removeFromCart(authentication.getName(), productId);
        return ResponseEntity.ok(Map.of("message", "Item removed!", "cart", cart));
    }

    // GET CART - GET /api/cart
    @GetMapping
    public ResponseEntity<?> getCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }
}
