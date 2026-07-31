package com.movieapp.src.dao;

import java.util.List;

public interface MovieDirectorDao {
    List<Integer> getDirectorIdsByMovie(int movieId);

    List<Integer> getMovieIdsByDirector(int directorId);
}
