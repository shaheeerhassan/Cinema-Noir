package com.movieapp.src.dao;

import com.movieapp.src.model.*;

import java.util.List;

public interface MovieDao {
    List<Movie> findAll();

    Movie findById(int movieId);

    List<Movie> searchByTitle(String keyword);

    List<Movie> findByYear(int year);

    List<Movie> findByGenre(int genreId);

    List<Movie> findByActor(int actorId);

    List<Movie> findByDirector(int directorId);
}
