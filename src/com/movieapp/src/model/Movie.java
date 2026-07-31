package com.movieapp.src.model;

public class Movie {
    private int movieId;
    private String title;
    private int releaseYear;
    private int runtime;
    private String description;
    private String posterUrl;
    private String contentRating;

    public Movie() {}

    public Movie(int movieId, String title, int releaseYear, int runtime, String description, String posterUrl, String contentRating) {
        this.movieId = movieId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.description = description;
        this.posterUrl = "/src/com/movieapp/resources/posters/"+posterUrl.replace("png","jpg");
        this.contentRating = contentRating;
    }

    // Getters & Setters
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public int getRuntime() { return runtime; }
    public void setRuntime(int runtime) { this.runtime = runtime; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContentRating() {
        return contentRating;
    }
    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
