package com.movieapp.src.service;

import com.movieapp.src.dao.*;
import com.movieapp.src.dto.MovieDetailsDTO;
import com.movieapp.src.dto.ReviewDTO;
import com.movieapp.src.model.*;

import java.util.List;

public class MovieService {
    private final MovieDao movieDAO;
    private final ActorDao actorDAO;
    private final DirectorDao directorDAO;
    private final GenreDao genreDAO;
    private final ReviewDao reviewDAO;

    public MovieService() {
        this.movieDAO = new MovieDaoImpl();
        this.actorDAO = new ActorDaoImpl();
        this.directorDAO = new DirectorDaoImpl();
        this.genreDAO = new GenreDaoImpl();
        this.reviewDAO = new ReviewDaoImpl();
    }

    public MovieDetailsDTO getMovieDetails(int movieId) {

        // 1. Get basic movie
        Movie movie = movieDAO.findById(movieId);

        // 2. Get related data
        List<Actor> actors = actorDAO.findByMovie(movieId);
        List<Director> directors = directorDAO.findByMovie(movieId);
        List<Genre> genres = genreDAO.findByMovie(movieId);

        // 3. Get reviews (Use ReviewService to get DTOs)
        ReviewService reviewService = new ReviewService();
        List<ReviewDTO> reviews = reviewService.getReviewsByMovie(movieId);

        // 4. Average rating
        double avgRating = reviewDAO.getAverageRating(movieId);

        // 5. Return DTO
        return new MovieDetailsDTO(
                movie,
                actors,
                directors,
                genres,
                reviews,
                avgRating
        );
    }
}
