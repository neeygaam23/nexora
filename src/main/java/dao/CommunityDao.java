package dao;

import model.Community;
import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommunityDao {
    public List<Community> listAll() throws SQLException {
        String sql = "SELECT * FROM communities ORDER BY created_at DESC";
        List<Community> out = new ArrayList<>();
        try (Connection c = DBConnectionUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Community cm = mapRow(rs);
                out.add(cm);
            }
        }
        return out;
    }

    public List<Community> listByCreator(int creatorId) throws SQLException {
        String sql = "SELECT * FROM communities WHERE creator_id = ? ORDER BY created_at DESC";
        List<Community> out = new ArrayList<>();
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

    public int create(Community community) throws SQLException {
        String sql = "INSERT INTO communities (name, description, creator_id) VALUES (?, ?, ?)";
        try (Connection c = DBConnectionUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, community.getName());
            ps.setString(2, community.getDescription());
            ps.setInt(3, community.getCreatorId());
            int affected = ps.executeUpdate();
            if (affected == 0)
                throw new SQLException("Creating community failed");
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next())
                    return keys.getInt(1);
            }
        }
        return -1;
    }

    public Community findById(int id) throws SQLException {
        String sql = "SELECT * FROM communities WHERE id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return mapRow(rs);
            }
        }
        return null;
    }

    public void update(Community community) throws SQLException {
        String sql = "UPDATE communities SET name = ?, description = ?, category = ?, banner_image = ?, is_private = ? WHERE id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, community.getName());
            ps.setString(2, community.getDescription());
            ps.setString(3, community.getCategory());
            ps.setString(4, community.getBannerImage());
            ps.setBoolean(5, community.isPrivate());
            ps.setInt(6, community.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int communityId) throws SQLException {
        String sql = "DELETE FROM communities WHERE id = ?";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, communityId);
            ps.executeUpdate();
        }
    }

    private Community mapRow(ResultSet rs) throws SQLException {
        Community cm = new Community();
        cm.setId(rs.getInt("id"));
        cm.setName(rs.getString("name"));
        cm.setDescription(rs.getString("description"));
        cm.setCategory(rs.getString("category"));
        cm.setBannerImage(rs.getString("banner_image"));
        cm.setPrivate(rs.getBoolean("is_private"));
        cm.setCreatorId(rs.getInt("creator_id"));
        cm.setCreatedAt(rs.getTimestamp("created_at"));
        return cm;
    }
}
