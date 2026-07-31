package com.movieapp.src.dao;

import com.movieapp.src.model.Director;

import java.util.List;

public interface DirectorDao {
    Director findById(int directorId);

    List<Director> findByMovie(int movieId);
}
