package com.movieapp.src.model;

import java.sql.Date;

public class Actor {
    private int actorId;
    private String fullName;
    private Date birthDate;
    private String photoUrl;

    public Actor() {}

    public Actor(int actorId, String fullName, Date birthDate, String photoUrl) {
        this.actorId = actorId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.photoUrl = "/src/com/movieapp/resources/actors/"+photoUrl.replace("png","jpg");
    }

    // getters & setters

    public int getActorId() {
        return actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}