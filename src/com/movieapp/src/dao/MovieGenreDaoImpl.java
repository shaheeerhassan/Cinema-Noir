package com.movieapp.src.dao;

import com.movieapp.src.config.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MovieGenreDaoImpl implements MovieGenreDao {

    @Override
    public List<Integer> getGenreIdsByMovie(int movieId) {
        List<Integer> genreIds = new ArrayList<>();
        String sql = """
            SELECT GenreID
            FROM movie_genres
            WHERE MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    genreIds.add(rs.getInt("GenreID"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return genreIds;
    }

    @Override
    public List<Integer> getMovieIdsByGenre(int genreId) {
        List<Integer> movieIds = new ArrayList<>();
        String sql = """
            SELECT MovieID
            FROM moviegenres
            WHERE GenreID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, genreId);

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
