package com.movieapp.src.dto;

public class WatchlistDTO {

    private int movieId;
    private String title;
    private int releaseYear;
    private String posterUrl;

    public WatchlistDTO() {}

    public WatchlistDTO(int movieId, String title, int releaseYear, String posterUrl) {
        this.movieId = movieId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.posterUrl = posterUrl;
    }

    // Getters & Setters

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
}