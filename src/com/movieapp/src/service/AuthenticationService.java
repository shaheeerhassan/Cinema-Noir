package com.movieapp.src.service;

import com.movieapp.src.dao.UserDao;
import com.movieapp.src.dao.UserDaoImpl;
import com.movieapp.src.exception.LoginException;
import com.movieapp.src.exception.SignUpException;
import com.movieapp.src.model.User;
import java.sql.Date;

public class AuthenticationService {

    private final UserDao userDAO;

    public AuthenticationService() {
        this.userDAO = new UserDaoImpl();
    }

    // Signup: Create new user
    public User signup(String username, String password) throws SignUpException {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            throw new SignUpException("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new SignUpException("Password cannot be empty");
        }

        // Check if username already exists
        User existingUser = userDAO.findByUsername(username);
        if (existingUser != null) {
            throw new SignUpException("Username already exists");
        }

        // Create new user
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setJoinDate(new Date(System.currentTimeMillis()));

        // Save to database
        userDAO.createUser(newUser);

        return newUser;
    }

    // Login: Verify user credentials
    public User login(String username, String password) throws LoginException {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            throw new LoginException("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new LoginException("Password cannot be empty");
        }

        // Find user by username
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new LoginException("Username not found");
        }

        // Verify password
        if (!user.getPassword().equals(password)) {
            throw new LoginException("Invalid password");
        }

        return user;
    }
}
