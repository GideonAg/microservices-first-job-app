package com.redeemerlives.reviewms.review;

import com.redeemerlives.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    private final ReviewsService reviewsService;
    private final ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewsService reviewsService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewsService = reviewsService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Reviews>> getAllReviews(@RequestParam int companyId) {
        return ResponseEntity.ok(reviewsService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam int companyId, @RequestBody Reviews reviewBody) {
        Boolean review = reviewsService.createReview(companyId, reviewBody);
        if (review) {
            reviewMessageProducer.sendMessage(reviewBody);
            return ResponseEntity.status(HttpStatus.CREATED).body("review created successfully");
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable int reviewId) {
        Reviews review = reviewsService.getReviewById(reviewId);

        if (review != null)
            return ResponseEntity.ok(review);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Reviews> updateReviewById(@PathVariable int reviewId, @RequestBody Reviews reviewUpdate) {
        Reviews review = reviewsService.updateReviewById(reviewId, reviewUpdate);

        if (review != null)
            return ResponseEntity.ok(review);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable int reviewId) {
        boolean deleted = reviewsService.deleteReviewById(reviewId);
        if (deleted)
            return ResponseEntity.ok("review deleted successfully");
        return new ResponseEntity<>("review not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam int companyId) {
        List<Reviews> reviews = reviewsService.getAllReviews(companyId);
        return reviews.stream().mapToDouble(Reviews::getRating).average().orElse(0.0);
    }
}
