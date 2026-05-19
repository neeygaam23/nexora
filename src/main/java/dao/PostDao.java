package dao;

import model.Post;
import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao {
    public int create(Post post) throws SQLException {
        String sql = "INSERT INTO posts (community_id, course_id, author_id, title, body) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DBConnectionUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (post.getCommunityId() > 0) {
                ps.setInt(1, post.getCommunityId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            if (post.getCourseId() > 0) {
                ps.setInt(2, post.getCourseId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, post.getAuthorId());
            ps.setString(4, post.getTitle());
            ps.setString(5, post.getBody());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("Creating post failed");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }

    public Post findById(int id) throws SQLException {
        String sql = "SELECT * FROM posts WHERE id = ?";
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

    public List<Post> listByCommunity(int communityId) throws SQLException {
        String sql = "SELECT * FROM posts WHERE community_id = ? ORDER BY created_at DESC";
        List<Post> out = new ArrayList<>();
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

    public void delete(int postId) throws SQLException {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        }
    }

    private Post mapRow(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getInt("id"));
        post.setCommunityId(rs.getInt("community_id"));
        post.setCourseId(rs.getInt("course_id"));
        post.setAuthorId(rs.getInt("author_id"));
        post.setTitle(rs.getString("title"));
        post.setBody(rs.getString("body"));
        post.setCreatedAt(rs.getTimestamp("created_at"));
        return post;
    }
}
