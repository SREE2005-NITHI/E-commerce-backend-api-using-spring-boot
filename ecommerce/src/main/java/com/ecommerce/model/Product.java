package com.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data @Entity @Table(name = "products")
@NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name required")
    private String name;

    private String description;

    @NotNull(message = "Price required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotNull(message = "Stock required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    private String imageUrl;

    private String brand;

    // Low stock threshold - alert if stock goes below this
    @Builder.Default
    private Integer lowStockThreshold = 10;

    @Builder.Default
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Review> reviews;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // Helper: check if low stock
    @Transient
    public boolean isLowStock() {
        return this.stock != null && this.stock <= this.lowStockThreshold;
    }
}
