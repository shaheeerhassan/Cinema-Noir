package com.movieapp.src.dao;

import com.movieapp.src.model.Movie;

import java.util.List;

public interface WatchlistDao {
    void addToWatchlist(int userId, int movieId);

    void removeFromWatchlist(int userId, int movieId);

    List<Movie> getUserWatchlist(int userId); // JOIN (Users + Watchlist + Movies)

    boolean exists(int userId, int movieId); // prevent duplicates
}
