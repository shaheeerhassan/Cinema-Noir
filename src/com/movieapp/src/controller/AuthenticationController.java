package com.movieapp.src.controller;

import com.movieapp.src.exception.LoginException;
import com.movieapp.src.exception.SignUpException;
import com.movieapp.src.model.User;
import com.movieapp.src.service.AuthenticationService;

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController() {
        this.authenticationService = new AuthenticationService();
    }

    // Handle user login
    public User login(String username, String password) throws LoginException {
        return authenticationService.login(username, password);
    }

    // Handle user signup
    public User signup(String username, String password) throws SignUpException {
        return authenticationService.signup(username, password);
    }
}