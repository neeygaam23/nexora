package util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DBConfigListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String url = sce.getServletContext().getInitParameter("dbUrl");
        String user = sce.getServletContext().getInitParameter("dbUser");
        String pass = sce.getServletContext().getInitParameter("dbPassword");
        try {
            Class<?> dbUtilClass = Class.forName("util.DBConnectionUtil");
            dbUtilClass.getMethod("init", String.class, String.class, String.class).invoke(null, url, user, pass);

            Class<?> schemaPatcherClass = Class.forName("util.DBSchemaPatcher");
            schemaPatcherClass.getMethod("applyBaselinePatches").invoke(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply startup DB schema patches", e);
        }
    }
}