package com.movieapp.src.service;

import com.movieapp.src.dao.*;
import com.movieapp.src.dto.WatchlistDTO;
import com.movieapp.src.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class WatchlistService {

    private final WatchlistDao watchlistDAO;

    public WatchlistService() {
        this.watchlistDAO = new WatchlistDaoImpl();
    }

    //Add a movie to user's watchlist

    public void addToWatchlist(int userId, int movieId) {
        if (!watchlistDAO.exists(userId, movieId)) {
            watchlistDAO.addToWatchlist(userId, movieId);
        }
    }

    //Remove a movie from user's watchlist

    public void removeFromWatchlist(int userId, int movieId) {
        watchlistDAO.removeFromWatchlist(userId, movieId);
    }

     //Retrieve user's watchlist

    public List<WatchlistDTO> getUserWatchlist(int userId) {

        List<Movie> movies = watchlistDAO.getUserWatchlist(userId);
        List<WatchlistDTO> result = new ArrayList<>();

        for (Movie m : movies) {
            result.add(new WatchlistDTO(
                    m.getMovieId(),
                    m.getTitle(),
                    m.getReleaseYear(),
                    m.getPosterUrl()
            ));
        }

        return result;
    }
}