package com.redeemerlives.reviewms.review.Impl;

import com.redeemerlives.reviewms.review.ReviewRepository;
import com.redeemerlives.reviewms.review.Reviews;
import com.redeemerlives.reviewms.review.ReviewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsServiceImpl implements ReviewsService {
    private final ReviewRepository reviewsRepository;

    public ReviewsServiceImpl(ReviewRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public List<Reviews> getAllReviews(int companyId) {
        return reviewsRepository.getAllByCompanyId(companyId);
    }

    @Override
    public Boolean createReview(Integer companyId, Reviews review) {
        if (companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewsRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Reviews getReviewById(int reviewId) {
        return reviewsRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Reviews updateReviewById(int reviewId, Reviews reviewUpdate) {
        Optional<Reviews> review = reviewsRepository.findById(reviewId);

        if (review.isPresent()) {
            review.get().setReviewBody(reviewUpdate.getReviewBody());
            review.get().setTitle(reviewUpdate.getTitle());
            review.get().setRating(reviewUpdate.getRating());
            review.get().setCompanyId(reviewUpdate.getCompanyId());
            return reviewsRepository.save(review.get());
        }

        return null;
    }

    @Override
    public Boolean deleteReviewById(int reviewId) {
        Optional<Reviews> review = reviewsRepository.findById(reviewId);

        if (review.isPresent()) {
            reviewsRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
