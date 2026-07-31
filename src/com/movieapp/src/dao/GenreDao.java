package com.movieapp.src.dao;

import com.movieapp.src.model.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll();

    Genre findById(int genreId);

    List<Genre> findByMovie(int movieId); // JOIN
}
