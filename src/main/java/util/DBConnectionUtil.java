package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Simple DB connection util for coursework. Replace URL/user/pass in
 * production.
 * Uses basic DriverManager; can be extended to a connection pool.
 */
public class DBConnectionUtil {
    private static String url;
    private static String user;
    private static String pass;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void init(String url, String user, String pass) {
        DBConnectionUtil.url = url;
        DBConnectionUtil.user = user;
        DBConnectionUtil.pass = pass;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}
