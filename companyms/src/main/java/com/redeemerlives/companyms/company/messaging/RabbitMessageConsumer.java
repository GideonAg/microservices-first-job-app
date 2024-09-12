package com.redeemerlives.companyms.company.messaging;

import com.redeemerlives.companyms.company.CompanyService;
import com.redeemerlives.companyms.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageConsumer {
    private final CompanyService companyService;

    public RabbitMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage) {
        companyService.updatedCompanyRating(reviewMessage);
    }
}
