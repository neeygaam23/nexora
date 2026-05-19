package dao;

import model.Progress;
import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProgressDao {
    public void markComplete(int enrollmentId, int moduleId) throws SQLException {
        String sql = "INSERT INTO progress (enrollment_id, module_id, is_completed, completed_at) VALUES (?, ?, TRUE, NOW()) "
                +
                "ON DUPLICATE KEY UPDATE is_completed = TRUE, completed_at = NOW()";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            ps.setInt(2, moduleId);
            ps.executeUpdate();
        }
    }

    public List<Progress> listByEnrollment(int enrollmentId) throws SQLException {
        String sql = "SELECT progress_id, enrollment_id, module_id, is_completed, completed_at FROM progress WHERE enrollment_id = ? ORDER BY module_id ASC";
        List<Progress> out = new ArrayList<>();
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(mapRow(rs));
                }
            }
        }
        return out;
    }

    public int countCompleted(int enrollmentId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM progress WHERE enrollment_id = ? AND is_completed = TRUE";
        try (Connection c = DBConnectionUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    private Progress mapRow(ResultSet rs) throws SQLException {
        Progress progress = new Progress();
        progress.setId(rs.getInt("progress_id"));
        progress.setEnrollmentId(rs.getInt("enrollment_id"));
        progress.setModuleId(rs.getInt("module_id"));
        progress.setCompleted(rs.getBoolean("is_completed"));
        progress.setCompletedAt(rs.getTimestamp("completed_at"));
        return progress;
    }
}
