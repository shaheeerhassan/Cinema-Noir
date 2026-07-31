package com.movieapp.src.dto;

public class MovieSearchDTO {

    private int movieId;
    private String title;
    private int releaseYear;
    private double averageRating;
    private String posterUrl;

    public MovieSearchDTO() {}

    public MovieSearchDTO(int movieId, String title, int releaseYear, double averageRating, String posterUrl) {
        this.movieId = movieId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.averageRating = averageRating;
        this.posterUrl = posterUrl;
    }

    // Getters & Setters

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
}