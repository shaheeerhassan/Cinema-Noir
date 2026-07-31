package com.movieapp.src.dao;

import com.movieapp.src.config.DBUtils;
import com.movieapp.src.model.User;
import java.sql.*;

public class UserDaoImpl implements UserDao {

    @Override
    public User findById(int userId) {
        User user = null;
        String sql = """
            SELECT UserID, Username, JoinDate, Password
            FROM users
            WHERE UserID = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("UserID"),
                            rs.getString("Username"),
                            rs.getDate("JoinDate"),
                            rs.getString("Password")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = null;
        String sql = """
            SELECT UserID, Username, JoinDate, Password
            FROM users
            WHERE Username = ?
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getInt("UserID"),
                            rs.getString("Username"),
                            rs.getDate("JoinDate"),
                            rs.getString("Password")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void createUser(User user) {
        String sql = """
            INSERT INTO users (Username, Password, JoinDate)
            VALUES (?, ?, ?)
            """;

        try (Connection cn = DBUtils.openConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setDate(3, new Date(System.currentTimeMillis()));

            ps.executeUpdate();

            // Optionally set the generated UserID back to the user object
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
