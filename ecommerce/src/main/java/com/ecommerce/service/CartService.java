package com.ecommerce.service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import org.springframework.stereotype.Service;

@Service
public class            CartService {

    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public CartService(CartRepository cartRepo, CartItemRepository cartItemRepo,
                        ProductRepository productRepo, UserRepository userRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    // Get or create cart for user
    public Cart getOrCreateCart(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return cartRepo.findByUserId(user.getId())
                .orElseGet(() -> cartRepo.save(Cart.builder().user(user).build()));
    }

    // ADD to cart
    public Cart addToCart(String userEmail, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        Cart cart = getOrCreateCart(userEmail);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (product.getStock() < quantity) {
            throw new BadRequestException("Insufficient stock! Available: " + product.getStock());
        }

        var existingItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), productId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepo.save(item);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();
            cart.getItems().add(newItem);
            cartItemRepo.save(newItem);
        }
        return cartRepo.findByUserId(cart.getUser().getId()).orElse(cart);
    }

    // UPDATE cart item quantity
    public Cart updateCartItem(String userEmail, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        Cart cart = getOrCreateCart(userEmail);
        CartItem item = cartItemRepo.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not in cart"));

        if (item.getProduct().getStock() < quantity) {
            throw new BadRequestException("Insufficient stock! Available: " + item.getProduct().getStock());
        }

        item.setQuantity(quantity);
        cartItemRepo.save(item);
        return cartRepo.findByUserId(cart.getUser().getId()).orElse(cart);
    }

    // REMOVE item from cart
    public Cart removeFromCart(String userEmail, Long productId) {
        Cart cart = getOrCreateCart(userEmail);
        CartItem item = cartItemRepo.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not in cart"));
        cart.getItems().remove(item);
        cartItemRepo.delete(item);
        return cartRepo.findByUserId(cart.getUser().getId()).orElse(cart);
    }

    // GET cart
    public Cart getCart(String userEmail) {
        return getOrCreateCart(userEmail);
    }

    // CLEAR cart (after order placed)
    public void clearCart(Cart cart) {
        cartItemRepo.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}
