package com.movieapp.src.model;

public class MovieGenre {
    private int movieGenreId;
    private int movieId;
    private int genreId;

    public MovieGenre() {}

    public MovieGenre(int movieGenreId, int movieId, int genreId) {
        this.movieGenreId = movieGenreId;
        this.movieId = movieId;
        this.genreId = genreId;
    }

    // getters & setters


    public int getMovieGenreId() {
        return movieGenreId;
    }

    public void setMovieGenreId(int movieGenreId) {
        this.movieGenreId = movieGenreId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}