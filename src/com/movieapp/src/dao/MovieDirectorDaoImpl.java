package com.movieapp.src.dao;

import com.movieapp.src.config.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MovieDirectorDaoImpl implements MovieDirectorDao {

    @Override
    public List<Integer> getDirectorIdsByMovie(int movieId) {
        List<Integer> directorIds = new ArrayList<>();
        String sql = """
            SELECT DirectorID
            FROM movie_directors
            WHERE MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    directorIds.add(rs.getInt("DirectorID"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return directorIds;
    }

    @Override
    public List<Integer> getMovieIdsByDirector(int directorId) {
        List<Integer> movieIds = new ArrayList<>();
        String sql = """
            SELECT MovieID
            FROM moviedirectors
            WHERE DirectorID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, directorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movieIds.add(rs.getInt("MovieID"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieIds;
    }
}
