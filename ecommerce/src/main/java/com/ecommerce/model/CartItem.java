package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Data @Entity @Table(name = "cart_items")
@NoArgsConstructor @AllArgsConstructor @Builder
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @ToString.Exclude
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @Transient
    public Double getSubtotal() {
        return product != null ? product.getPrice() * quantity : 0.0;
    }
}
