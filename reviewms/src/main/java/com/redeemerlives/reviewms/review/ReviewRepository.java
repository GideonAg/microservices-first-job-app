package com.redeemerlives.reviewms.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Integer> {

    List<Reviews> getAllByCompanyId(int companyId);

    Reviews getReviewsByIdAndCompanyId(int companyId, int reviewId);

    Reviews getReviewsById(int reviewId);
}
