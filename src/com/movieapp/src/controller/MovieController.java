package com.movieapp.src.controller;

import com.movieapp.src.dto.MovieDetailsDTO;
import com.movieapp.src.service.MovieService;

public class MovieController {

    private final MovieService movieService;

    public MovieController() {
        this.movieService = new MovieService();
    }

    public MovieDetailsDTO getMovieDetails(int movieId) {
        return movieService.getMovieDetails(movieId);
    }
}