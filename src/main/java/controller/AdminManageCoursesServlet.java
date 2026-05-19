package controller;

import dao.CourseDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/manage-courses")
public class AdminManageCoursesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CourseDao courseDao = new CourseDao();
        try {
            req.setAttribute("courses", courseDao.listAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/manage-courses.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to load courses", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String courseId = req.getParameter("courseId");

        CourseDao courseDao = new CourseDao();
        try {
            if ("delete".equalsIgnoreCase(action)) {
                courseDao.delete(Integer.parseInt(courseId));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/manage-courses");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Failed to manage course", e);
        }
    }
}
