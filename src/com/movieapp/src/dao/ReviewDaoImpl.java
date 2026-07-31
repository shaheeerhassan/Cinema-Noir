package com.movieapp.src.dao;

import com.movieapp.src.config.DBUtils;
import com.movieapp.src.model.Review;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class ReviewDaoImpl implements ReviewDao {

    @Override
    public void addReview(Review review) {
        String sql = """
            INSERT INTO reviews (UserID, MovieID, Rating, ReviewText, ReviewDate)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getMovieId());
            ps.setDouble(3, review.getRating());
            ps.setString(4, review.getReviewText());
            ps.setDate(5, review.getReviewDate());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Review> getReviewsByMovie(int movieId) {
        List<Review> reviews = new ArrayList<>();
        String sql = """
            SELECT ReviewID, UserID, MovieID, Rating, ReviewText, ReviewDate
            FROM reviews
            WHERE MovieID = ?
            ORDER BY ReviewDate DESC
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review(
                            rs.getInt("ReviewID"),
                            rs.getInt("UserID"),
                            rs.getInt("MovieID"),
                            rs.getDouble("Rating"),
                            rs.getString("ReviewText"),
                            rs.getDate("ReviewDate")
                    );
                    reviews.add(review);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reviews;
    }

    @Override
    public double getAverageRating(int movieId) {
        String sql = """
            SELECT AVG(Rating) as avg_rating
            FROM reviews
            WHERE MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0; // No reviews found
    }

    @Override
    public boolean hasUserReviewed(int userId, int movieId) {
        String sql = """
            SELECT COUNT(*) > 0 as has_reviewed
            FROM reviews
            WHERE UserID = ? AND MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("has_reviewed");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
