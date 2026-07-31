package com.movieapp.src.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection openConnection() throws SQLException {
            String url = "jdbc:mysql://localhost:3306/movie_management";
            String user = "root";
            String pass = "Shaheer07";
            return DriverManager.getConnection(url, user, pass);
    }
}


