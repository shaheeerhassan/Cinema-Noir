package com.movieapp.src.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection openConnection() throws SQLException {
            String url = "jdbc:mysql://localhost:3306/movie_management OR YOUR_DB_URL";
            String user = "DB_USER";
            String pass = "DB_PASSWORD";
            return DriverManager.getConnection(url, user, pass);
    }
}


