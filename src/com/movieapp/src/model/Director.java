package com.movieapp.src.model;

import java.sql.Date;

public class Director {
    private int directorId;
    private String fullName;
    private Date birthDate;

    public Director() {}

    public Director(int directorId, String fullName, Date birthDate) {
        this.directorId = directorId;
        this.fullName = fullName;
        this.birthDate = birthDate;
    }

    // getters & setters

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
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
}