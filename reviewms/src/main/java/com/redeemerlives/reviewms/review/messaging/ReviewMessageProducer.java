package com.redeemerlives.reviewms.review.messaging;

import com.redeemerlives.reviewms.review.Reviews;
import com.redeemerlives.reviewms.review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Reviews reviews) {
        ReviewMessage reviewMessage = new ReviewMessage();
        reviewMessage.setId(reviews.getId());
        reviewMessage.setDescription(reviews.getReviewBody());
        reviewMessage.setTitle(reviews.getTitle());
        reviewMessage.setRating(reviews.getRating());
        reviewMessage.setCompanyId(reviews.getCompanyId());
        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
    }
}
