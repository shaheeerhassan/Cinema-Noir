package com.movieapp.src.dao;

import com.movieapp.src.config.DBUtils;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MovieCastDaoImpl implements MovieCastDao {

    @Override
    public List<Integer> getActorIdsByMovie(int movieId) {
        List<Integer> actorIds = new ArrayList<>();
        String sql = """
            SELECT ActorID
            FROM movie_cast
            WHERE MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    actorIds.add(rs.getInt("ActorID"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return actorIds;
    }

    @Override
    public List<Integer> getMovieIdsByActor(int actorId) {
        List<Integer> movieIds = new ArrayList<>();
        String sql = """
            SELECT MovieID
            FROM moviecast
            WHERE ActorID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, actorId);

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
