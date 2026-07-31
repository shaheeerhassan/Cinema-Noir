package com.movieapp.src.model;

import java.sql.Date;

public class User {
    private int userId;
    private String username;
    private String password;
    private Date joinDate;

    public User() {}

    public User(int userId, String username, Date joinDate, String password) {
        this.userId = userId;
        this.username = username;
        this.joinDate = joinDate;
        this.password = password;
    }

    // getters & setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}