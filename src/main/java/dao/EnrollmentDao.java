package dao;

import util.DBConnectionUtil;

import java.sql.*;

public class EnrollmentDao {
    public boolean isEnrolled(int courseId, int userId) throws SQLException {
        String sql = "SELECT 1 FROM enrollments WHERE course_id = ? AND user_id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void enroll(int courseId, int userId) throws SQLException {
        String sql = "INSERT IGNORE INTO enrollments (course_id, user_id) VALUES (?, ?)";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public void unenroll(int courseId, int userId) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE course_id = ? AND user_id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
}
