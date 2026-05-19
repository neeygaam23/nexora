package controller;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/manage-users")
public class AdminManageUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        try {
            req.setAttribute("users", userDao.listAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/manage-users.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to load users", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String userIdParam = req.getParameter("userId");

        UserDao userDao = new UserDao();
        try {
            int userId = Integer.parseInt(userIdParam);

            if ("deactivate".equalsIgnoreCase(action)) {
                userDao.deactivate(userId);
            } else if ("delete".equalsIgnoreCase(action)) {
                userDao.delete(userId);
            }

            resp.sendRedirect(req.getContextPath() + "/admin/manage-users");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Failed to manage user", e);
        }
    }
}
