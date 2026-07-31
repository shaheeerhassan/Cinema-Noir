package com.movieapp.src.dao;

import java.util.List;

public interface MovieCastDao {
    List<Integer> getActorIdsByMovie(int movieId);

    List<Integer> getMovieIdsByActor(int actorId);
}
