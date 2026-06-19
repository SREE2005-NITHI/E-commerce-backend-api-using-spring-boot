package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    List<Product> findByCategoryIdAndActiveTrue(Long categoryId);

    @Query("SELECT p FROM Product p WHERE " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND p.active = true")
    List<Product> searchProducts(@Param("keyword") String keyword);

    List<Product> findByPriceBetweenAndActiveTrue(Double min, Double max);

    // Low stock alert - products below their threshold
    @Query("SELECT p FROM Product p WHERE p.stock <= p.lowStockThreshold AND p.active = true")
    List<Product> findLowStockProducts();

    List<Product> findByBrandIgnoreCaseAndActiveTrue(String brand);
}
