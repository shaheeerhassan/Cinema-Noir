package com.movieapp.src.dto;

import com.movieapp.src.model.*;
import java.util.List;

public class MovieDetailsDTO {

    private Movie movie;
    private List<Actor> actors;
    private List<Director> directors;
    private List<Genre> genres;
    private List<ReviewDTO> reviews;
    private double averageRating;

    public MovieDetailsDTO() {}

    public MovieDetailsDTO(Movie movie,
                           List<Actor> actors,
                           List<Director> directors,
                           List<Genre> genres,
                           List<ReviewDTO> reviews,
                           double averageRating) {
        this.movie = movie;
        this.actors = actors;
        this.directors = directors;
        this.genres = genres;
        this.reviews = reviews;
        this.averageRating = averageRating;
    }

    // Getters & Setters

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public List<Actor> getActors() { return actors; }
    public void setActors(List<Actor> actors) { this.actors = actors; }

    public List<Director> getDirectors() { return directors; }
    public void setDirectors(List<Director> directors) { this.directors = directors; }

    public List<Genre> getGenres() { return genres; }
    public void setGenres(List<Genre> genres) { this.genres = genres; }

    public List<ReviewDTO> getReviews() { return reviews; }
    public void setReviews(List<ReviewDTO> reviews) { this.reviews = reviews; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
}
