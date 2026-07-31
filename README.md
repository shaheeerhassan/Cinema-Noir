# 🎬 Cinema Noir

A high-performance, lightweight **RESTful Movie Database Backend API** built entirely in **pure Java** as a 2nd-semester Database course project. 

By eliminating heavyweight frameworks like Spring, servlet containers, or external JSON libraries, this project achieves near-zero overhead. It relies solely on the JDK's native networking capabilities and raw JDBC.

### 👥 Authors
*   **Shaheer Hassan**
*   **Moiz Hakro**
*   **Saim Abdullah**

---

## 🚀 Key Features

*   🔍 **Advanced Search & Filter:** Global movie listing with multi-parameter filtering (keyword, release year, genre).
*   🎭 **Deep Relations:** Comprehensive entity mapping linking movies to cast members, directors, and genres.
*   🔐 **Core Authentication:** Secure user signup and session endpoints (`/api/auth/*`).
*   📋 **Personalized Watchlists:** User-specific bookmarking engine to add, remove, and track movies.
*   ⭐ **Interactive Reviews:** Unique per-user rating and review system with dynamic average score updates.
*   🌐 **Cross-Origin Ready:** Native CORS handling built from scratch to support any modern frontend.

---

## 🛠️ Tech Stack & Constraints

*   **Runtime:** Java JDK (Built-in `com.sun.net.httpserver.HttpServer`)
*   **Database:** MySQL + Native JDBC Driver (`mysql-connector-j`)
*   **Architecture:** Clean **Layered Architecture** (Controller ➔ Service ➔ DAO ➔ Database)
*   **Zero-Dependency Design:** Hand-rolled custom HTTP routing, JSON serialization string builders, and CORS header injection.

---

## 📁 Project Architecture

```text
src/com/movieapp/
├── ApiServer.java          # Server entry point & central HTTP route registry
├── config/                 # DBConnection utilities & lifecycle management
├── controller/             # HTTP request parsers, route handlers & status codes
├── service/                # Core business logic, validation, & transaction rules
├── dao/                    # Pure JDBC Data Access Objects (SQL queries & mapping)
├── model/                  # Domain entities (Movie, Actor, Director, Review, User)
├── dto/                    # Data Transfer Objects for optimized JSON responses
├── exception/              # Structured custom exceptions (e.g., SignUpException)
└── resources/posters/      # Static storage for movie poster assets
```

---

## 🛰️ API Reference

### Public Endpoints

| Method | Endpoint | Query Parameters | Description |
| :--- | :--- | :--- | :--- |
| **GET** | `/api/movies` | `keyword`, `year`, `genreId` | Paginated search and multi-filter discovery |
| **GET** | `/api/movies/details` | `id` *(Required)* | Full movie payload + cast metadata + reviews |
| **GET** | `/api/movies/actor` | `id` *(Required)* | Fetch filmography data for a specific actor |
| **GET** | `/api/actors` | `keyword` | Search and discover cast members |
| **GET** | `/api/genres` | None | Fetch all available movie categories |

### User & Interactive Endpoints

| Method | Endpoint | Request Body / Action | Description |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/auth/signup` | JSON `User` credentials | Register a new user profile |
| **POST** | `/api/auth/login` | JSON `User` credentials | Authenticate user session |
| **GET** | `/api/watchlist` | `userId` *(Required)* | Retrieve a user's bookmarked titles |
| **POST** | `/api/watchlist` | `action=add\|remove` | Modify user watchlist state |
| **GET** | `/api/reviews` | `movieId` *(Required)* | Fetch public reviews for a target movie |
| **POST** | `/api/reviews` | JSON `Review` object | Submit a movie rating and review |

---

## ⚙️ Getting Started

### Prerequisites
*   Java JDK 17 or higher
*   MySQL Server instance

### Installation & Run

1.  **Database Setup:**
    Create a local MySQL instance named `movie_management` and execute your schema initialization script.
    ```sql
    CREATE DATABASE movie_management;
    ```

2.  **Configuration:**
    Navigate to `src/com/movieapp/config/DBUtils.java` and update your database credentials:
    ```java
    private static final String URL = "jdbc:mysql://localhost:3606/movie_management";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";
    ```

3.  **IDE Import:**
    Open the root directory as a plain Java project inside **IntelliJ IDEA** or **Eclipse**. No build tools (Maven/Gradle) are required.

4.  **Launch:**
    Execute the `main` method inside `ApiServer.java`. The API will boot up locally on `http://localhost:8080`.

---
