package controller;

import dao.CommunityDao;
import dao.CourseDao;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        CommunityDao communityDao = new CommunityDao();
        CourseDao courseDao = new CourseDao();

        try {
            req.setAttribute("users", userDao.listAll());
            req.setAttribute("communities", communityDao.listAll());
            req.setAttribute("courses", courseDao.listAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/admin-dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to load admin dashboard data", e);
        }
    }
}