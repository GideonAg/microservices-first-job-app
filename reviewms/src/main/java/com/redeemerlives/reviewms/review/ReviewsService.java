package com.redeemerlives.reviewms.review;

import java.util.List;

public interface ReviewsService {
    List<Reviews> getAllReviews(int companyId);

    Boolean createReview(Integer company, Reviews review);

    Reviews getReviewById(int reviewId);

    Boolean deleteReviewById(int reviewId);

    Reviews updateReviewById(int reviewId, Reviews reviewUpdate);
}
