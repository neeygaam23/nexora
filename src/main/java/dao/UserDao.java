package dao;

import model.User;
import util.DBConnectionUtil;

import java.sql.*;
import java.util.Optional;

/**
 * UserDao with prepared statements. Provides basic user queries and insertion.
 */
public class UserDao {

    // Find user by username and include role name
    public static Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.username = ?";
        try (Connection conn = DBConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = mapRowToUser(rs);
                    return Optional.of(u);
                }
            }
        }
        return Optional.empty();
    }

    // Find by email
    public Optional<User> findByEmail(String email) throws SQLException {
        String sql = "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ?";
        try (Connection conn = DBConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToUser(rs));
                }
            }
        }
        return Optional.empty();
    }

    // Create user - returns generated id
    public int createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password_hash, role_id, full_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setInt(4, user.getRoleId());
            ps.setString(5, user.getFullName());
            int affected = ps.executeUpdate();
            if (affected == 0)
                throw new SQLException("Creating user failed, no rows affected.");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    // Helper to map a ResultSet row to User
    private static User mapRowToUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setRoleId(rs.getInt("role_id"));
        u.setFullName(rs.getString("full_name"));
        u.setCreatedAt(rs.getTimestamp("created_at"));
        try {
            String rn = rs.getString("role_name");
            u.setRoleName(rn);
        } catch (SQLException ignored) {
            // role_name may not be selected in some queries
        }
        return u;
    }
}
