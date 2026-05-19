package controller;

import dao.EnrollmentDao;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/courses/enroll")
public class EnrollCourseServlet extends HttpServlet {
    private EnrollmentDao enrollmentDao = new EnrollmentDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User current = (User) req.getSession().getAttribute("currentUser");
        if (current == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        try {
            enrollmentDao.enroll(courseId, current.getId());
            resp.sendRedirect(req.getContextPath() + "/courses/view?course_id=" + courseId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
