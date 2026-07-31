package com.movieapp.src.dao;

import com.movieapp.src.config.DBUtils;
import com.movieapp.src.model.Director;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DirectorDaoImpl implements DirectorDao {

    @Override
    public Director findById(int directorId) {
        Director director = null;
        String sql = """
            SELECT DirectorID, FullName, BirthDate
            FROM directors
            WHERE DirectorID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, directorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    director = new Director(
                            rs.getInt("DirectorID"),
                            rs.getString("FullName"),
                            rs.getDate("BirthDate")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return director;
    }

    @Override
    public List<Director> findByMovie(int movieId) {
        List<Director> directors = new ArrayList<>();
        String sql = """
            SELECT d.DirectorID, d.FullName, d.BirthDate
            FROM directors d
            INNER JOIN movie_directors md ON d.DirectorID = md.DirectorID
            WHERE md.MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Director director = new Director(
                            rs.getInt("DirectorID"),
                            rs.getString("FullName"),
                            rs.getDate("BirthDate")
                    );
                    directors.add(director);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return directors;
    }
}
