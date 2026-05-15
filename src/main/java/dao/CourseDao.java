package dao;

import model.Course;
import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    public int create(Course course) throws SQLException {
        String sql = "INSERT INTO courses (community_id, title, description, creator_id) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnectionUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, course.getCommunityId());
            ps.setString(2, course.getTitle());
            ps.setString(3, course.getDescription());
            ps.setInt(4, course.getCreatorId());
            int affected = ps.executeUpdate();
            if (affected == 0)
                throw new SQLException("Creating course failed");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next())
                    return keys.getInt(1);
            }
        }
        return -1;
    }

    public Course findById(int id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course();
                    course.setId(rs.getInt("id"));
                    course.setCommunityId(rs.getInt("community_id"));
                    course.setTitle(rs.getString("title"));
                    course.setDescription(rs.getString("description"));
                    course.setCreatorId(rs.getInt("creator_id"));
                    course.setCreatedAt(rs.getTimestamp("created_at"));
                    return course;
                }
            }
        }
        return null;
    }

    public List<Course> listByCommunity(int communityId) throws SQLException {
        String sql = "SELECT * FROM courses WHERE community_id = ? ORDER BY created_at DESC";
        List<Course> out = new ArrayList<>();
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, communityId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setId(rs.getInt("id"));
                    course.setCommunityId(rs.getInt("community_id"));
                    course.setTitle(rs.getString("title"));
                    course.setDescription(rs.getString("description"));
                    course.setCreatorId(rs.getInt("creator_id"));
                    course.setCreatedAt(rs.getTimestamp("created_at"));
                    out.add(course);
                }
            }
        }
        return out;
    }
}
