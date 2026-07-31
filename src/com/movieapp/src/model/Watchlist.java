package com.movieapp.src.model;

public class Watchlist {
    private int watchlistId;
    private int userId;
    private int movieId;

    public Watchlist() {}

    public Watchlist(int watchlistId, int userId, int movieId) {
        this.watchlistId = watchlistId;
        this.userId = userId;
        this.movieId = movieId;
    }

    // getters & setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getWatchlistId() {
        return watchlistId;
    }

    public void setWatchlistId(int watchlistId) {
        this.watchlistId = watchlistId;
    }
}