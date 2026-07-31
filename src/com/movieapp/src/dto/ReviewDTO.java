package com.movieapp.src.dto;

public class ReviewDTO {

    private String username;
    private double rating;
    private String comment;

    public ReviewDTO() {}

    public ReviewDTO(String username, double rating, String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters & Setters

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}