package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ADD PRODUCT - POST /api/products?categoryId=1
    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product,
                                         @RequestParam(required = false) Long categoryId) {
        Product created = productService.createProduct(product, categoryId);
        return ResponseEntity.ok(Map.of("message", "Product created successfully!", "product", created));
    }

    // GET ALL - GET /api/products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // GET ONE - GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // UPDATE PRODUCT - PUT /api/products/{id}?categoryId=1
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                            @Valid @RequestBody Product product,
                                            @RequestParam(required = false) Long categoryId) {
        Product updated = productService.updateProduct(id, product, categoryId);
        return ResponseEntity.ok(Map.of("message", "Product updated successfully!", "product", updated));
    }

    // DELETE PRODUCT - DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("message", productService.deleteProduct(id)));
    }

    // SEARCH - GET /api/products/search?keyword=phone
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    // FILTER by category - GET /api/products/category/{categoryId}
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> byCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getByCategory(categoryId));
    }

    // FILTER by price range - GET /api/products/filter/price?min=100&max=5000
    @GetMapping("/filter/price")
    public ResponseEntity<List<Product>> byPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(productService.getByPriceRange(min, max));
    }

    // FILTER by brand - GET /api/products/filter/brand?brand=Apple
    @GetMapping("/filter/brand")
    public ResponseEntity<List<Product>> byBrand(@RequestParam String brand) {
        return ResponseEntity.ok(productService.getByBrand(brand));
    }

    // LOW STOCK ALERT - GET /api/products/low-stock (Admin only)
    @GetMapping("/low-stock")
    public ResponseEntity<?> lowStock() {
        List<Product> products = productService.getLowStockProducts();
        return ResponseEntity.ok(Map.of(
                "count", products.size(),
                "message", products.isEmpty() ? "No low stock products!" : "Low stock alert!",
                "products", products
        ));
    }
}
