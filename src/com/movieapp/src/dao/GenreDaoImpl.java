package com.movieapp.src.dao;

import com.movieapp.src.config.DBUtils;
import com.movieapp.src.model.Genre;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class GenreDaoImpl implements GenreDao {

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        String sql = """
            SELECT GenreID, GenreName
            FROM genres
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Genre genre = new Genre(
                        rs.getInt("GenreID"),
                        rs.getString("GenreName")
                );
                genres.add(genre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return genres;
    }

    @Override
    public Genre findById(int genreId) {
        Genre genre = null;
        String sql = """
            SELECT GenreID, GenreName
            FROM genres
            WHERE GenreID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, genreId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    genre = new Genre(
                            rs.getInt("GenreID"),
                            rs.getString("GenreName")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return genre;
    }

    @Override
    public List<Genre> findByMovie(int movieId) {
        List<Genre> genres = new ArrayList<>();
        String sql = """
            SELECT g.GenreID, g.GenreName
            FROM genres g
            INNER JOIN movie_genres mg ON g.GenreID = mg.GenreID
            WHERE mg.MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Genre genre = new Genre(
                            rs.getInt("GenreID"),
                            rs.getString("GenreName")
                    );
                    genres.add(genre);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return genres;
    }
}
