package com.movieapp.src.model;

public class MovieDirector {
    private int movieDirectorId;
    private int movieId;
    private int directorId;

    public MovieDirector() {}

    public MovieDirector(int movieDirectorId, int movieId, int directorId) {
        this.movieDirectorId = movieDirectorId;
        this.movieId = movieId;
        this.directorId = directorId;
    }

    // getters & setters


    public int getMovieDirectorId() {
        return movieDirectorId;
    }

    public void setMovieDirectorId(int movieDirectorId) {
        this.movieDirectorId = movieDirectorId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }
}