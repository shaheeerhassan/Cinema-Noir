package com.movieapp.src;

import com.movieapp.src.controller.*;
import com.movieapp.src.dto.*;
import com.movieapp.src.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiServer {

    private static final int PORT = 8080;
    private static final SearchController searchController = new SearchController();
    private static final com.movieapp.src.service.SearchService searchService = new com.movieapp.src.service.SearchService();
    private static final MovieController movieController = new MovieController();
    private static final ActorController actorController = new ActorController();
    private static final AuthenticationController authController = new AuthenticationController();
    private static final WatchlistController watchlistController = new WatchlistController();
    private static final ReviewController reviewController = new ReviewController();
    private static final com.movieapp.src.dao.GenreDao genreDao = new com.movieapp.src.dao.GenreDaoImpl();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Movies Endpoints
        server.createContext("/api/movies", new MoviesHandler());
        server.createContext("/api/movies/details", new MovieDetailsHandler());
        
        // Genres Endpoint
        server.createContext("/api/genres", new GenresHandler());
        
        // Actors Endpoints
        server.createContext("/api/actors", new ActorsHandler());
        server.createContext("/api/movies/actor", new ActorMoviesHandler());
        
        // Auth Endpoints
        server.createContext("/api/auth/login", new LoginHandler());
        server.createContext("/api/auth/signup", new SignupHandler());
        
        // Watchlist Endpoints
        server.createContext("/api/watchlist", new WatchlistHandler());
        
        // Reviews Endpoints
        server.createContext("/api/reviews", new ReviewsHandler());

        server.setExecutor(null);
        System.out.println("API Server started on port " + PORT);
        server.start();
    }

    // --- Handlers ---

    static class MoviesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());
                List<MovieSearchDTO> movies;
                
                if (params.containsKey("keyword")) {
                    movies = searchController.search(params.get("keyword"));
                } else if (params.containsKey("year") || params.containsKey("genreId")) {
                    Integer year = params.get("year") == null || params.get("year").isEmpty() ? null : Integer.parseInt(params.get("year"));
                    Integer genreId = params.get("genreId") == null || params.get("genreId").isEmpty() ? null : Integer.parseInt(params.get("genreId"));
                    movies = searchController.filter(year, genreId);
                } else {
                    movies = searchController.getAllMovies();
                }
                
                sendJsonResponse(exchange, toJson(movies), 200);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class MovieDetailsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());
                int id = Integer.parseInt(params.get("id"));
                MovieDetailsDTO details = movieController.getMovieDetails(id);
                sendJsonResponse(exchange, toJson(details), 200);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class ActorsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());
                List<ActorDTO> actors;
                if (params.containsKey("keyword")) {
                    actors = actorController.searchActors(params.get("keyword"));
                } else {
                    actors = actorController.getAllActors();
                }
                sendJsonResponse(exchange, toJson(actors), 200);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = getRequestBody(exchange);
                Map<String, String> data = parseJsonSimple(body);
                try {
                    User user = authController.login(data.get("username"), data.get("password"));
                    sendJsonResponse(exchange, toJson(user), 200);
                } catch (Exception e) {
                    sendJsonResponse(exchange, "{\"message\":\"" + e.getMessage() + "\"}", 401);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class SignupHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                String body = getRequestBody(exchange);
                Map<String, String> data = parseJsonSimple(body);
                try {
                    User user = authController.signup(data.get("username"), data.get("password"));
                    sendJsonResponse(exchange, toJson(user), 201);
                } catch (Exception e) {
                    sendJsonResponse(exchange, "{\"message\":\"" + e.getMessage() + "\"}", 400);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class WatchlistHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            String method = exchange.getRequestMethod();
            if ("GET".equalsIgnoreCase(method)) {
                Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());
                int userId = Integer.parseInt(params.get("userId"));
                List<WatchlistDTO> list = watchlistController.getWatchlist(userId);
                sendJsonResponse(exchange, toJson(list), 200);
            } else if ("POST".equalsIgnoreCase(method)) {
                String body = getRequestBody(exchange);
                Map<String, String> data = parseJsonSimple(body);
                int userId = Integer.parseInt(data.get("userId"));
                int movieId = Integer.parseInt(data.get("movieId"));
                String action = data.getOrDefault("action", "add");
                
                if ("remove".equals(action)) {
                    watchlistController.remove(userId, movieId);
                } else {
                    watchlistController.add(userId, movieId);
                }
                sendJsonResponse(exchange, "{\"success\":true}", 200);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class ReviewsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            String method = exchange.getRequestMethod();
            if ("GET".equalsIgnoreCase(method)) {
                Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());
                int movieId = Integer.parseInt(params.get("movieId"));
                List<ReviewDTO> reviews = reviewController.getReviews(movieId);
                sendJsonResponse(exchange, toJson(reviews), 200);
            } else if ("POST".equalsIgnoreCase(method)) {
                String body = getRequestBody(exchange);
                Map<String, String> data = parseJsonSimple(body);
                int userId = Integer.parseInt(data.get("userId"));
                int movieId = Integer.parseInt(data.get("movieId"));
                double rating = Double.parseDouble(data.get("rating"));
                String comment = data.get("comment");
                
                reviewController.addReview(userId, movieId, rating, comment);
                sendJsonResponse(exchange, "{\"success\":true}", 201);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class ActorMoviesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());
                int actorId = Integer.parseInt(params.get("id"));
                List<MovieSearchDTO> movies = searchService.filterByActor(actorId);
                sendJsonResponse(exchange, toJson(movies), 200);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class GenresHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                List<com.movieapp.src.model.Genre> genres = genreDao.findAll();
                sendJsonResponse(exchange, toJson(genres), 200);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    // --- Utilities ---

    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
    }

    private static void sendJsonResponse(HttpExchange exchange, String json, int code) throws IOException {
        byte[] response = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }

    private static String getRequestBody(HttpExchange exchange) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static Map<String, String> parseQueryParams(String query) {
        if (query == null) return Collections.emptyMap();
        Map<String, String> params = new HashMap<>();
        for (String pair : query.split("&")) {
            String[] split = pair.split("=");
            if (split.length > 1) {
                params.put(split[0], URLDecoder.decode(split[1], StandardCharsets.UTF_8));
            } else {
                params.put(split[0], "");
            }
        }
        return params;
    }

    private static Map<String, String> parseJsonSimple(String body) {
        Map<String, String> map = new HashMap<>();
        body = body.trim();
        if (body.startsWith("{") && body.endsWith("}")) {
            // Regex to match "key": "value" or "key": value
            Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*:\\s*(?:\"([^\"]*)\"|([^,{}]+))");
            Matcher matcher = pattern.matcher(body);
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2) != null ? matcher.group(2) : matcher.group(3).trim();
                map.put(key, value);
            }
        }
        return map;
    }

    // --- Minimal Manual JSON Converter ---
    // (In a real project, use Gson or Jackson)
    
    private static String toJson(Object obj) {
        if (obj == null) return "null";
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            return "[" + list.stream().map(ApiServer::toJson).collect(Collectors.joining(",")) + "]";
        }
        if (obj instanceof MovieSearchDTO) {
            MovieSearchDTO m = (MovieSearchDTO) obj;
            return String.format("{\"movieId\":%d,\"title\":\"%s\",\"releaseYear\":%d,\"averageRating\":%.1f,\"posterUrl\":\"%s\"}",
                    m.getMovieId(), escapeJson(m.getTitle()), m.getReleaseYear(), m.getAverageRating(), escapeJson(m.getPosterUrl()));
        }
        if (obj instanceof MovieDetailsDTO) {
            MovieDetailsDTO d = (MovieDetailsDTO) obj;
            return String.format("{\"movie\":%s,\"actors\":%s,\"directors\":%s,\"genres\":%s,\"reviews\":%s,\"averageRating\":%.1f}",
                    toJson(d.getMovie()), toJson(d.getActors()), toJson(d.getDirectors()), toJson(d.getGenres()), toJson(d.getReviews()), d.getAverageRating());
        }
        if (obj instanceof com.movieapp.src.model.Movie) {
            com.movieapp.src.model.Movie m = (com.movieapp.src.model.Movie) obj;
            return String.format("{\"movieId\":%d,\"title\":\"%s\",\"description\":\"%s\",\"releaseYear\":%d,\"posterUrl\":\"%s\"}",
                    m.getMovieId(), escapeJson(m.getTitle()), escapeJson(m.getDescription()), m.getReleaseYear(), escapeJson(m.getPosterUrl()));
        }
        if (obj instanceof com.movieapp.src.model.Actor) {
            com.movieapp.src.model.Actor a = (com.movieapp.src.model.Actor) obj;
            return String.format("{\"actorId\":%d,\"fullName\":\"%s\",\"photoUrl\":\"%s\"}",
                    a.getActorId(), escapeJson(a.getFullName()), escapeJson(a.getPhotoUrl()));
        }
        if (obj instanceof ActorDTO) {
            ActorDTO a = (ActorDTO) obj;
            return String.format("{\"actorId\":%d,\"fullName\":\"%s\",\"photoUrl\":\"%s\"}",
                    a.getActorId(), escapeJson(a.getFullName()), escapeJson(a.getPhotoUrl()));
        }
        if (obj instanceof com.movieapp.src.model.Director) {
            com.movieapp.src.model.Director d = (com.movieapp.src.model.Director) obj;
            return String.format("{\"directorId\":%d,\"fullName\":\"%s\"}", d.getDirectorId(), escapeJson(d.getFullName()));
        }
        if (obj instanceof com.movieapp.src.model.Genre) {
            com.movieapp.src.model.Genre g = (com.movieapp.src.model.Genre) obj;
            return String.format("{\"genreId\":%d,\"genreName\":\"%s\"}", g.getGenreId(), escapeJson(g.getGenreName()));
        }
        if (obj instanceof ReviewDTO) {
            ReviewDTO r = (ReviewDTO) obj;
            return String.format("{\"username\":\"%s\",\"rating\":%.1f,\"comment\":\"%s\"}",
                    escapeJson(r.getUsername()), r.getRating(), escapeJson(r.getComment()));
        }
        if (obj instanceof User) {
            User u = (User) obj;
            return String.format("{\"userId\":%d,\"username\":\"%s\"}", u.getUserId(), escapeJson(u.getUsername()));
        }
        if (obj instanceof WatchlistDTO) {
            WatchlistDTO w = (WatchlistDTO) obj;
            return String.format("{\"movieId\":%d,\"title\":\"%s\",\"releaseYear\":%d,\"posterUrl\":\"%s\"}",
                    w.getMovieId(), escapeJson(w.getTitle()), w.getReleaseYear(), escapeJson(w.getPosterUrl()));
        }
        return "\"" + escapeJson(obj.toString()) + "\"";
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
