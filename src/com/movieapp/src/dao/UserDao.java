package com.movieapp.src.dao;

import com.movieapp.src.model.User;

public interface UserDao {
    User findById(int userId);

    User findByUsername(String username);

    void createUser(User user);
}
