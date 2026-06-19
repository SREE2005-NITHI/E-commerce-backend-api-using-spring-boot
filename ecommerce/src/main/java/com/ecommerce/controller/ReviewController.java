package com.ecommerce.controller;

import com.ecommerce.model.Review;
import com.ecommerce.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ADD REVIEW - POST /api/reviews/product/{productId}
    @PostMapping("/product/{productId}")
    public ResponseEntity<?> addReview(@PathVariable Long productId,
                                        @RequestBody Map<String, Object> body,
                                        Authentication authentication) {
        String userEmail = authentication.getName();
        Integer rating = Integer.valueOf(body.get("rating").toString());
        String comment = body.get("comment").toString();

        Review review = reviewService.addReview(productId, userEmail, rating, comment);
        return ResponseEntity.ok(Map.of("message", "Review added successfully!", "review", review));
    }

    // GET REVIEWS for a product - GET /api/reviews/product/{productId}
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductReviews(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getReviewsByProduct(productId);
        Double avgRating = reviewService.getAverageRating(productId);
        return ResponseEntity.ok(Map.of(
                "averageRating", avgRating,
                "totalReviews", reviews.size(),
                "reviews", reviews
        ));
    }
}
