package com.movieapp.src.controller;

import com.movieapp.src.dto.ReviewDTO;
import com.movieapp.src.service.ReviewService;

import java.util.List;

public class ReviewController {

    private ReviewService reviewService;

    public ReviewController() {
        this.reviewService = new ReviewService();
    }

    public void addReview(int userId, int movieId, double rating, String comment) {
        reviewService.addReview(userId, movieId, rating, comment);
    }

    public List<ReviewDTO> getReviews(int movieId) {
        return reviewService.getReviewsByMovie(movieId);
    }

    public double getAverageRating(int movieId) {
        return reviewService.getAverageRating(movieId);
    }
}