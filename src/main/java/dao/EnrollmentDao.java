package dao;

import util.DBConnectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class EnrollmentDao {
    public int findEnrollmentId(int courseId, int userId) throws SQLException {
        String sql = "SELECT id FROM enrollments WHERE course_id = ? AND user_id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    public List<Integer> listCourseIdsByUser(int userId) throws SQLException {
        String sql = "SELECT course_id FROM enrollments WHERE user_id = ? ORDER BY enrolled_at DESC";
        List<Integer> courseIds = new ArrayList<>();
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    courseIds.add(rs.getInt("course_id"));
                }
            }
        }
        return courseIds;
    }

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
        enroll(courseId, userId, "free", 0.0);
    }

    public void enroll(int courseId, int userId, String paymentStatus, double amountPaid) throws SQLException {
        String sql = "INSERT INTO enrollments (course_id, user_id, payment_status, amount_paid) VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE payment_status = VALUES(payment_status), amount_paid = VALUES(amount_paid)";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.setInt(2, userId);
            ps.setString(3, paymentStatus);
            ps.setDouble(4, amountPaid);
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
