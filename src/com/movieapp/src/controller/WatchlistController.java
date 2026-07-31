package com.movieapp.src.controller;

import com.movieapp.src.dto.WatchlistDTO;
import com.movieapp.src.service.WatchlistService;

import java.util.List;

public class WatchlistController {

    private WatchlistService watchlistService;

    public WatchlistController() {
        this.watchlistService = new WatchlistService();
    }

    public void add(int userId, int movieId) {
        watchlistService.addToWatchlist(userId, movieId);
    }

    public void remove(int userId, int movieId) {
        watchlistService.removeFromWatchlist(userId, movieId);
    }

    public List<WatchlistDTO> getWatchlist(int userId) {
        return watchlistService.getUserWatchlist(userId);
    }
}