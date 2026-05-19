package dao;

import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommunityMemberDao {
    public boolean isMember(int communityId, int userId) throws SQLException {
        String sql = "SELECT 1 FROM community_members WHERE community_id = ? AND user_id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, communityId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void join(int communityId, int userId) throws SQLException {
        String sql = "INSERT IGNORE INTO community_members (community_id, user_id) VALUES (?, ?)";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, communityId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public void leave(int communityId, int userId) throws SQLException {
        String sql = "DELETE FROM community_members WHERE community_id = ? AND user_id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, communityId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public List<Integer> listMembers(int communityId) throws SQLException {
        String sql = "SELECT user_id FROM community_members WHERE community_id = ?";
        List<Integer> out = new ArrayList<>();
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, communityId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    out.add(rs.getInt(1));
            }
        }
        return out;
    }
}
