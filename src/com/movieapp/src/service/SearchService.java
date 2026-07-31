package com.movieapp.src.service;

import com.movieapp.src.dao.*;
import com.movieapp.src.dto.MovieSearchDTO;
import com.movieapp.src.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchService {

    private final MovieDao movieDAO;
    private final ReviewDao reviewDAO;

    public SearchService() {
        this.movieDAO = new MovieDaoImpl();
        this.reviewDAO = new ReviewDaoImpl();
    }


    //Search movies by keyword (title)
    public List<MovieSearchDTO> searchMovies(String keyword) {
        List<Movie> movies = movieDAO.searchByTitle(keyword);
        return convertToSearchDTO(movies);
    }


    //Filter movies by year

    public List<MovieSearchDTO> filterByYear(int year) {
        List<Movie> movies = movieDAO.findByYear(year);
        return convertToSearchDTO(movies);
    }


    //Filter movies by genre

    public List<MovieSearchDTO> filterByGenre(int genreId) {
        List<Movie> movies = movieDAO.findByGenre(genreId);
        return convertToSearchDTO(movies);
    }


    //Filter movies by actor

    public List<MovieSearchDTO> filterByActor(int actorId) {
        List<Movie> movies = movieDAO.findByActor(actorId);
        return convertToSearchDTO(movies);
    }


    // Apply combined filters for movies

    public List<MovieSearchDTO> filterMovies(Integer year, Integer genreId) {
        List<Movie> movies;

        if (year != null && genreId != null) {
            List<Movie> byYear = movieDAO.findByYear(year);
            List<Movie> byGenre = movieDAO.findByGenre(genreId);
            movies = intersect(byYear, byGenre);
        } else if (year != null) {
            movies = movieDAO.findByYear(year);
        } else if (genreId != null) {
            movies = movieDAO.findByGenre(genreId);
        } else {
            movies = movieDAO.findAll();
        }

        return convertToSearchDTO(movies);
    }


    //Retrieve all movies

    public List<MovieSearchDTO> getAllMovies() {
        List<Movie> movies = movieDAO.findAll();
        return convertToSearchDTO(movies);
    }


     //Convert Movie to MovieSearchDTO with average rating

    private List<MovieSearchDTO> convertToSearchDTO(List<Movie> movies) {
        List<MovieSearchDTO> result = new ArrayList<>();

        for (Movie m : movies) {
            double avgRating = reviewDAO.getAverageRating(m.getMovieId());
            MovieSearchDTO dto = new MovieSearchDTO(
                    m.getMovieId(),
                    m.getTitle(),
                    m.getReleaseYear(),
                    avgRating,
                    m.getPosterUrl()
            );
            result.add(dto);
        }

        return result;
    }


    //Find intersection of two movie lists by movie ID
    private List<Movie> intersect(List<Movie> list1, List<Movie> list2) {

        List<Movie> result = new ArrayList<>();

        for (Movie m1 : list1) {
            for (Movie m2 : list2) {
                if (m1.getMovieId() == m2.getMovieId()) {
                    result.add(m1);
                    break;
                }
            }
        }

        return result;
    }
}
