package com.movieapp.src.service;

import com.movieapp.src.dao.ActorDao;
import com.movieapp.src.dao.ActorDaoImpl;
import com.movieapp.src.model.Actor;

import java.util.List;

public class ActorService {

    private final ActorDao actorDAO;

    public ActorService() {
        this.actorDAO = new ActorDaoImpl();
    }

    // Get all actors
    public List<Actor> getAllActors() {
        return actorDAO.findAll();
    }

    // Search actors by name
    public List<Actor> searchActors(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllActors();
        }
        return actorDAO.findByName(keyword);
    }
}
