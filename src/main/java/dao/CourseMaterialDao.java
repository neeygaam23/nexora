package dao;

import model.Course;
import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseMaterialDao {
    public int create(int courseId, int uploaderId, String filename, String storedPath, String contentType,
            String mediaType) throws SQLException {
        try (Connection c = DBConnectionUtil.getConnection()) {
            boolean hasContentType = hasColumn(c, "course_materials", "content_type");
            boolean hasMediaType = hasColumn(c, "course_materials", "media_type");
            String sql;
            if (hasContentType && hasMediaType) {
                sql = "INSERT INTO course_materials (course_id, uploader_id, filename, stored_path, content_type, media_type) VALUES (?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO course_materials (course_id, uploader_id, filename, stored_path) VALUES (?, ?, ?, ?)";
            }
            try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, courseId);
                ps.setInt(2, uploaderId);
                ps.setString(3, filename);
                ps.setString(4, storedPath);
                if (hasContentType && hasMediaType) {
                    ps.setString(5, contentType);
                    ps.setString(6, mediaType);
                }
                int affected = ps.executeUpdate();
                if (affected == 0)
                    throw new SQLException("Creating material failed");
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next())
                        return keys.getInt(1);
                }
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
                    String filenameValue = rs.getString("filename");
                    out.add(new String[] { String.valueOf(rs.getInt("id")), filenameValue,
                            rs.getString("stored_path"), null, detectMediaType(filenameValue) });
                }
            }
        }
        return out;
    }

    private boolean hasColumn(Connection connection, String tableName, String columnName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, columnName)) {
            return columns.next();
        }
    }

    private String detectMediaType(String filename) {
        String lower = filename == null ? "" : filename.toLowerCase();
        if (lower.endsWith(".mp4") || lower.endsWith(".webm") || lower.endsWith(".ogg") || lower.endsWith(".mov")
                || lower.endsWith(".m4v") || lower.endsWith(".mkv")) {
            return "video";
        }
        return "file";
    }
}
