package dao;

import util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DAO responsible for handling post reactions (LIKE feature).
 * Supports checking likes, counting likes, and toggling like/unlike.
 */
public class PostReactionDao {

    /**
     * Checks whether a user has already liked a specific post.
     */
    public boolean hasLiked(int postId, int userId) throws SQLException {

        String sql = """
                SELECT 1
                FROM post_reactions
                WHERE post_id = ?
                  AND user_id = ?
                  AND reaction_type = 'LIKE'
                """;

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // if a row exists → user already liked
            }
        }
    }

    /**
     * Returns total number of likes for a given post.
     */
    public int countLikes(int postId) throws SQLException {

        String sql = """
                SELECT COUNT(*) AS total
                FROM post_reactions
                WHERE post_id = ?
                  AND reaction_type = 'LIKE'
                """;

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, postId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }

        return 0; // fallback if no data found
    }

    /**
     * Toggles like/unlike for a post:
     * - If already liked → remove like
     * - If not liked → add like
     */
    public void toggleLike(int postId, int userId) throws SQLException {

        // Step 1: If user already liked → remove the like
        if (hasLiked(postId, userId)) {

            String deleteSql = """
                    DELETE FROM post_reactions
                    WHERE post_id = ?
                      AND user_id = ?
                      AND reaction_type = 'LIKE'
                    """;

            try (Connection conn = DBConnectionUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(deleteSql)) {

                ps.setInt(1, postId);
                ps.setInt(2, userId);
                ps.executeUpdate();
            }

            return;
        }

        // Step 2: Otherwise insert a new like
        String insertSql = """
                INSERT INTO post_reactions (post_id, user_id, reaction_type)
                VALUES (?, ?, 'LIKE')
                """;

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, postId);
            ps.setInt(2, userId);

            ps.executeUpdate();
        }
    }
}