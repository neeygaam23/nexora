package dao;

import model.Course;
import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseMaterialDao {
    public int create(int courseId, int uploaderId, String filename, String storedPath) throws SQLException {
        String sql = "INSERT INTO course_materials (course_id, uploader_id, filename, stored_path) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnectionUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, courseId);
            ps.setInt(2, uploaderId);
            ps.setString(3, filename);
            ps.setString(4, storedPath);
            int affected = ps.executeUpdate();
            if (affected == 0)
                throw new SQLException("Creating material failed");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next())
                    return keys.getInt(1);
            }
        }
        return -1;
    }

    public List<String[]> listByCourse(int courseId) throws SQLException {
        String sql = "SELECT id, filename, stored_path FROM course_materials WHERE course_id = ? ORDER BY created_at DESC";
        List<String[]> out = new ArrayList<>();
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new String[] { String.valueOf(rs.getInt("id")), rs.getString("filename"),
                            rs.getString("stored_path") });
                }
            }
        }
        return out;
    }
}
