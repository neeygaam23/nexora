package dao;

import model.Course;
import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    public List<Course> listAll() throws SQLException {
        String sql = "SELECT * FROM courses ORDER BY created_at DESC";
        List<Course> out = new ArrayList<>();
        try (Connection c = DBConnectionUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(mapRow(rs));
            }
        }
        return out;
    }

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
                    return mapRow(rs);
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
                    out.add(mapRow(rs));
                }
            }
        }
        return out;
    }

    public List<Course> listByCreator(int creatorId) throws SQLException {
        String sql = "SELECT * FROM courses WHERE creator_id = ? ORDER BY created_at DESC";
        List<Course> out = new ArrayList<>();
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, creatorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(mapRow(rs));
                }
            }
        }
        return out;
    }

    public void update(Course course) throws SQLException {
        String sql = "UPDATE courses SET title = ?, description = ?, is_paid = ?, price = ? WHERE id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setBoolean(3, course.isPaid());
            ps.setDouble(4, course.getPrice());
            ps.setInt(5, course.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int courseId) throws SQLException {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.executeUpdate();
        }
    }

    private Course mapRow(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setCommunityId(rs.getInt("community_id"));
        course.setTitle(rs.getString("title"));
        course.setDescription(rs.getString("description"));
        course.setPaid(rs.getBoolean("is_paid"));
        course.setPrice(rs.getDouble("price"));
        course.setCreatorId(rs.getInt("creator_id"));
        course.setCreatedAt(rs.getTimestamp("created_at"));
        return course;
    }
}
