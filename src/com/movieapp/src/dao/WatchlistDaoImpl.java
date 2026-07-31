package com.movieapp.src.dao;

import com.movieapp.src.config.DBUtils;
import com.movieapp.src.model.Movie;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class WatchlistDaoImpl implements WatchlistDao {

    @Override
    public void addToWatchlist(int userId, int movieId) {
        String sql = """
            INSERT IGNORE INTO watchlist (UserID, MovieID)
            VALUES (?, ?)
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, movieId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromWatchlist(int userId, int movieId) {
        String sql = """
            DELETE FROM watchlist
            WHERE UserID = ? AND MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, movieId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Movie> getUserWatchlist(int userId) {
        List<Movie> movies = new ArrayList<>();
        String sql = """
            SELECT DISTINCT m.MovieID, m.Title, m.ReleaseYear, m.Runtime, 
                           m.Description, m.PosterURL, m.ContentRating
            FROM movies m
            INNER JOIN watchlist w ON m.MovieID = w.MovieID
            WHERE w.UserID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie(
                            rs.getInt("MovieID"),
                            rs.getString("Title"),
                            rs.getInt("ReleaseYear"),
                            rs.getInt("Runtime"),
                            rs.getString("Description"),
                            rs.getString("PosterURL"),
                            rs.getString("ContentRating")
                    );
                    movies.add(movie);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public boolean exists(int userId, int movieId) {
        String sql = """
            SELECT COUNT(*) > 0 as ex
            FROM watchlist
            WHERE UserID = ? AND MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("ex");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}