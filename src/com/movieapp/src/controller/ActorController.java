package com.movieapp.src.controller;

import com.movieapp.src.dto.ActorDTO;
import com.movieapp.src.model.Actor;
import com.movieapp.src.service.ActorService;

import java.util.ArrayList;
import java.util.List;

public class ActorController {

    private final ActorService actorService;

    public ActorController() {
        this.actorService = new ActorService();
    }

    // Get all actors
    public List<ActorDTO> getAllActors() {
        List<Actor> actors = actorService.getAllActors();
        List<ActorDTO> dtos = new ArrayList<>();
        for (Actor actor : actors) {
            ActorDTO dto = new ActorDTO(
                    actor.getActorId(),
                    actor.getFullName(),
                    actor.getBirthDate(),
                    actor.getPhotoUrl()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // Search actors by keyword
    public List<ActorDTO> searchActors(String keyword) {
        List<Actor> actors = actorService.searchActors(keyword);
        List<ActorDTO> dtos = new ArrayList<>();
        for (Actor actor : actors) {
            ActorDTO dto = new ActorDTO(
                    actor.getActorId(),
                    actor.getFullName(),
                    actor.getBirthDate(),
                    actor.getPhotoUrl()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
