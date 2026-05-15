package dao;

import util.DBConnectionUtil;

import java.sql.*;

public class FollowDao {
    public void follow(int followerId, int followedId) throws SQLException {
        String sql = "INSERT IGNORE INTO user_follows (follower_id, followed_id) VALUES (?, ?)";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, followerId);
            ps.setInt(2, followedId);
            ps.executeUpdate();
        }
    }

    public void unfollow(int followerId, int followedId) throws SQLException {
        String sql = "DELETE FROM user_follows WHERE follower_id = ? AND followed_id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, followerId);
            ps.setInt(2, followedId);
            ps.executeUpdate();
        }
    }

    public boolean isFollowing(int followerId, int followedId) throws SQLException {
        String sql = "SELECT 1 FROM user_follows WHERE follower_id = ? AND followed_id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, followerId);
            ps.setInt(2, followedId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
