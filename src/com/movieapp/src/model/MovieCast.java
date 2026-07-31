package com.movieapp.src.model;

public class MovieCast {
    private int castId;
    private int movieId;
    private int actorId;

    public MovieCast() {}

    public MovieCast(int castId, int movieId, int actorId) {
        this.castId = castId;
        this.movieId = movieId;
        this.actorId = actorId;
    }

    // getters & setters

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
}