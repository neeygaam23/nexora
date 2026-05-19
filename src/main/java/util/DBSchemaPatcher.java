package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Applies additive schema fixes to keep older local databases compatible
 * with current DAO queries.
 */
public final class DBSchemaPatcher {
    private DBSchemaPatcher() {
    }

    public static void applyBaselinePatches() throws SQLException {
        try (Connection connection = DBConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            addColumnIfMissing(connection, "users", "profile_picture", "profile_picture VARCHAR(255)");
            addColumnIfMissing(connection, "users", "bio", "bio TEXT");
            addColumnIfMissing(connection, "users", "is_active", "is_active BOOLEAN DEFAULT TRUE");

            addColumnIfMissing(connection, "communities", "category", "category VARCHAR(100)");
            addColumnIfMissing(connection, "communities", "banner_image", "banner_image VARCHAR(255)");
            addColumnIfMissing(connection, "communities", "is_private", "is_private BOOLEAN DEFAULT FALSE");

            addColumnIfMissing(connection, "courses", "is_paid", "is_paid BOOLEAN DEFAULT FALSE");
            addColumnIfMissing(connection, "courses", "price", "price DECIMAL(10,2) DEFAULT 0.00");

            addColumnIfMissing(connection, "enrollments", "payment_status",
                    "payment_status ENUM('free','paid') DEFAULT 'free'");
            addColumnIfMissing(connection, "enrollments", "amount_paid", "amount_paid DECIMAL(10,2) DEFAULT 0.00");

            statement.execute("CREATE TABLE IF NOT EXISTS community_members ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "community_id INT NOT NULL, "
                    + "user_id INT NOT NULL, "
                    + "joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "UNIQUE KEY uq_community_member (community_id, user_id), "
                    + "FOREIGN KEY (community_id) REFERENCES communities(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)");

            statement.execute("CREATE TABLE IF NOT EXISTS user_follows ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "follower_id INT NOT NULL, "
                    + "followed_id INT NOT NULL, "
                    + "followed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "UNIQUE KEY uq_follow (follower_id, followed_id), "
                    + "FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (followed_id) REFERENCES users(id) ON DELETE CASCADE)");

            statement.execute("CREATE TABLE IF NOT EXISTS course_materials ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "course_id INT NOT NULL, "
                    + "uploader_id INT NULL, "
                    + "filename VARCHAR(255) NOT NULL, "
                    + "stored_path VARCHAR(1024) NOT NULL, "
                    + "content_type VARCHAR(100), "
                    + "media_type VARCHAR(20) DEFAULT 'file', "
                    + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (uploader_id) REFERENCES users(id) ON DELETE SET NULL)");

            addColumnIfMissing(connection, "course_materials", "content_type", "content_type VARCHAR(100)");
            addColumnIfMissing(connection, "course_materials", "media_type", "media_type VARCHAR(20) DEFAULT 'file'");

            statement.execute("CREATE TABLE IF NOT EXISTS post_reactions ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "post_id INT NOT NULL, "
                    + "user_id INT NOT NULL, "
                    + "reaction_type VARCHAR(20) NOT NULL DEFAULT 'LIKE', "
                    + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "UNIQUE KEY uq_post_reaction (post_id, user_id, reaction_type), "
                    + "FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)");

            statement.execute("CREATE TABLE IF NOT EXISTS comments ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "post_id INT NOT NULL, "
                    + "author_id INT NOT NULL, "
                    + "parent_comment_id INT NULL, "
                    + "body TEXT NOT NULL, "
                    + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (parent_comment_id) REFERENCES comments(id) ON DELETE CASCADE)");

            addColumnIfMissing(connection, "comments", "parent_comment_id", "parent_comment_id INT NULL");

            // Keep progress independent from a modules table so local setups without
            // course_modules can still track completion by module_id.
            statement.execute("CREATE TABLE IF NOT EXISTS progress ("
                    + "progress_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "enrollment_id INT NOT NULL, "
                    + "module_id INT NOT NULL, "
                    + "is_completed BOOLEAN DEFAULT FALSE, "
                    + "completed_at TIMESTAMP NULL, "
                    + "UNIQUE KEY uq_progress_enrollment_module (enrollment_id, module_id), "
                    + "CONSTRAINT fk_progress_enrollment "
                    + "FOREIGN KEY (enrollment_id) REFERENCES enrollments(id) ON DELETE CASCADE)");

            // Ensure roles exist with expected IDs
            ensureRoles(connection);
        }
    }

    private static void ensureRoles(Connection connection) throws SQLException {
        String sql = "INSERT IGNORE INTO roles (id, name) VALUES (1,'ADMIN'),(2,'CREATOR'),(3,'MEMBER')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    private static void addColumnIfMissing(Connection connection, String tableName, String columnName,
            String columnDefinition) throws SQLException {
        String existsSql = "SELECT COUNT(*) FROM information_schema.columns "
                + "WHERE table_schema = DATABASE() AND table_name = ? AND column_name = ?";
        try (PreparedStatement ps = connection.prepareStatement(existsSql)) {
            ps.setString(1, tableName);
            ps.setString(2, columnName);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    return;
                }
            }
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute("ALTER TABLE " + tableName + " ADD COLUMN " + columnDefinition);
        }
    }
}
