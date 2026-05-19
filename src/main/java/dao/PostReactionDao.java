package dao;

import util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostReactionDao {
    public boolean hasLiked(int postId, int userId) throws SQLException {
        String sql = "SELECT 1 FROM post_reactions WHERE post_id = ? AND user_id = ? AND reaction_type = 'LIKE'";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int countLikes(int postId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM post_reactions WHERE post_id = ? AND reaction_type = 'LIKE'";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public void toggleLike(int postId, int userId) throws SQLException {
        if (hasLiked(postId, userId)) {
            String deleteSql = "DELETE FROM post_reactions WHERE post_id = ? AND user_id = ? AND reaction_type = 'LIKE'";
            try (Connection c = DBConnectionUtil.getConnection();
                    PreparedStatement ps = c.prepareStatement(deleteSql)) {
                ps.setInt(1, postId);
                ps.setInt(2, userId);
                ps.executeUpdate();
            }
            return;
        }

        String insertSql = "INSERT INTO post_reactions (post_id, user_id, reaction_type) VALUES (?, ?, 'LIKE')";
        try (Connection c = DBConnectionUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, postId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
}