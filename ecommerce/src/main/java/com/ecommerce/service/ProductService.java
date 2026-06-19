package com.ecommerce.service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    // CREATE
    public Product createProduct(Product product, Long categoryId) {
        if (categoryId != null) {
            Category category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
            product.setCategory(category);
        }
        return productRepo.save(product);
    }

    // READ ALL
    public List<Product> getAllProducts() {
        return productRepo.findByActiveTrue();
    }

    // READ ONE
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    // UPDATE
    public Product updateProduct(Long id, Product updated, Long categoryId) {
        Product existing = getProductById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        existing.setImageUrl(updated.getImageUrl());
        existing.setBrand(updated.getBrand());
        if (updated.getLowStockThreshold() != null) {
            existing.setLowStockThreshold(updated.getLowStockThreshold());
        }
        if (categoryId != null) {
            Category category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
            existing.setCategory(category);
        }
        return productRepo.save(existing);
    }

    // DELETE (soft delete)
    public String deleteProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(false);
        productRepo.save(product);
        return "Product deleted successfully!";
    }

    // SEARCH
    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }

    // FILTER - category
    public List<Product> getByCategory(Long categoryId) {
        return productRepo.findByCategoryIdAndActiveTrue(categoryId);
    }

    // FILTER - price range
    public List<Product> getByPriceRange(Double min, Double max) {
        return productRepo.findByPriceBetweenAndActiveTrue(min, max);
    }

    // FILTER - brand
    public List<Product> getByBrand(String brand) {
        return productRepo.findByBrandIgnoreCaseAndActiveTrue(brand);
    }

    // LOW STOCK ALERT
    public List<Product> getLowStockProducts() {
        return productRepo.findLowStockProducts();
    }

    // Internal use - reduce stock when order placed
    public void reduceStock(Long productId, Integer quantity) {
        Product product = getProductById(productId);
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }
        product.setStock(product.getStock() - quantity);
        productRepo.save(product);
    }

    // Internal use - restore stock when order cancelled
    public void restoreStock(Long productId, Integer quantity) {
        Product product = getProductById(productId);
        product.setStock(product.getStock() + quantity);
        productRepo.save(product);
    }
}
