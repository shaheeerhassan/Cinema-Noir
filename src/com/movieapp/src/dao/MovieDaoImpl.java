package com.movieapp.src.dao;

import com.movieapp.src.config.DBUtils;
import com.movieapp.src.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MovieDaoImpl implements MovieDao {

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        String sql = """
            SELECT MovieID, Title, ReleaseYear, Runtime, Description, 
                   PosterURL, ContentRating
            FROM movies
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("MovieID"),
                        rs.getString("Title"),
                        rs.getInt("ReleaseYear"),
                        rs.getInt("Runtime"),
                        rs.getString("Description"),
                        rs.getString("PosterURL"),
                        rs.getString("ContentRating")
                );
                movies.add(movie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public Movie findById(int movieId) {
        Movie movie = null;
        String sql = """
            SELECT MovieID, Title, ReleaseYear, Runtime, Description,
                   PosterURL, ContentRating
            FROM movies
            WHERE MovieID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    movie = new Movie(
                            rs.getInt("MovieID"),
                            rs.getString("Title"),
                            rs.getInt("ReleaseYear"),
                            rs.getInt("Runtime"),
                            rs.getString("Description"),
                            rs.getString("PosterURL"),
                            rs.getString("ContentRating")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    @Override
    public List<Movie> searchByTitle(String keyword) {
        List<Movie> movies = new ArrayList<>();
        String sql = """
            SELECT MovieID, Title, ReleaseYear, Runtime, Description,
                   PosterURL, ContentRating
            FROM movies
            WHERE Title LIKE ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie(
                            rs.getInt("MovieID"),
                            rs.getString("Title"),
                            rs.getInt("ReleaseYear"),
                            rs.getInt("Runtime"),
                            rs.getString("Description"),
                            rs.getString("PosterURL"),
                            rs.getString("ContentRating")
                    );
                    movies.add(movie);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public List<Movie> findByYear(int year) {
        List<Movie> movies = new ArrayList<>();
        String sql = """
            SELECT MovieID, Title, ReleaseYear, Runtime, Description, 
                   PosterURL, ContentRating
            FROM movies
            WHERE ReleaseYear = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie(
                            rs.getInt("MovieID"),
                            rs.getString("Title"),
                            rs.getInt("ReleaseYear"),
                            rs.getInt("Runtime"),
                            rs.getString("Description"),
                            rs.getString("PosterURL"),
                            rs.getString("ContentRating")
                    );
                    movies.add(movie);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public List<Movie> findByGenre(int genreId) {
        List<Movie> movies = new ArrayList<>();
        String sql = """
            SELECT DISTINCT m.MovieID, m.Title, m.ReleaseYear, m.Runtime,
                           m.Description, m.PosterURL, m.ContentRating
            FROM movies m
            INNER JOIN movie_genres mg ON m.MovieID = mg.MovieID
            WHERE mg.GenreID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, genreId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie(
                            rs.getInt("MovieID"),
                            rs.getString("Title"),
                            rs.getInt("ReleaseYear"),
                            rs.getInt("Runtime"),
                            rs.getString("Description"),
                            rs.getString("PosterURL"),
                            rs.getString("ContentRating")
                    );
                    movies.add(movie);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public List<Movie> findByActor(int actorId) {
        List<Movie> movies = new ArrayList<>();
        String sql = """
            SELECT DISTINCT m.MovieID, m.Title, m.ReleaseYear, m.Runtime, 
                           m.Description, m.PosterURL, m.ContentRating
            FROM movies m
            INNER JOIN movie_cast mc ON m.MovieID = mc.MovieID
            WHERE mc.ActorID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, actorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie(
                            rs.getInt("MovieID"),
                            rs.getString("Title"),
                            rs.getInt("ReleaseYear"),
                            rs.getInt("Runtime"),
                            rs.getString("Description"),
                            rs.getString("PosterURL"),
                            rs.getString("ContentRating")
                    );
                    movies.add(movie);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    public List<Movie> findByDirector(int directorId) {
        List<Movie> movies = new ArrayList<>();
        String sql = """
            SELECT DISTINCT m.MovieID, m.Title, m.ReleaseYear, m.Runtime, 
                           m.Description, m.PosterURL, m.ContentRating
            FROM movies m
            INNER JOIN moviedirectors md ON m.MovieID = md.MovieID
            WHERE md.DirectorID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, directorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie(
                            rs.getInt("MovieID"),
                            rs.getString("Title"),
                            rs.getInt("ReleaseYear"),
                            rs.getInt("Runtime"),
                            rs.getString("Description"),
                            rs.getString("PosterURL"),
                            rs.getString("ContentRating")
                    );
                    movies.add(movie);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
}
