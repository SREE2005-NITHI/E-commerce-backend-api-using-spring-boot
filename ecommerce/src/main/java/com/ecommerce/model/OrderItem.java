package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Data @Entity @Table(name = "order_items")
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    private String productName; // snapshot at order time

    private Double priceAtOrder; // snapshot at order time

    private Integer quantity;

    @Transient
    public Double getSubtotal() {
        return priceAtOrder * quantity;
    }
}
