package com.redeemerlives.reviewms.review;

import jakarta.persistence.*;

@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String reviewBody;
    private String title;
    private double rating;

    private int companyId;

    public Reviews() {

    }

    public Reviews(int id, String reviewBody, String title, double rating, int companyId) {
        this.id = id;
        this.reviewBody = reviewBody;
        this.title = title;
        this.rating = rating;
        this.companyId = companyId;
    }

    public Reviews(String reviewBody, int companyId) {
        this.reviewBody = reviewBody;
        this.companyId = companyId;
    }

    public Reviews(String reviewBody, int companyId, String title, double rating) {
        this.reviewBody = reviewBody;
        this.companyId = companyId;
        this.title = title;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }


}
