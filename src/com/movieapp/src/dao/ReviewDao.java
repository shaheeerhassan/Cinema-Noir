package com.movieapp.src.dao;

import com.movieapp.src.model.Review;

import java.util.List;

public interface ReviewDao {
    void addReview(Review review);

    List<Review> getReviewsByMovie(int movieId);

    double getAverageRating(int movieId);

    boolean hasUserReviewed(int userId, int movieId);
}
