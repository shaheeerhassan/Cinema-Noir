package com.movieapp.src.dao;

import java.util.List;

public interface MovieGenreDao {
    List<Integer> getGenreIdsByMovie(int movieId);

    List<Integer> getMovieIdsByGenre(int genreId);
}
