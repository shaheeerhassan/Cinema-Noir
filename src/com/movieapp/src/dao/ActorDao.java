package com.movieapp.src.dao;

import com.movieapp.src.model.Actor;

import java.util.List;

public interface ActorDao {
    List<Actor> findAll();

    Actor findById(int actorId);

    List<Actor> findByMovie(int movieId);

    List<Actor> findByName(String name);
}
