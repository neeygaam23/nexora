package controller;

import dao.EnrollmentDao;
import dao.ProgressDao;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/courses/progress")
public class MarkProgressServlet extends HttpServlet {
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();
    private final ProgressDao progressDao = new ProgressDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int courseId = Integer.parseInt(req.getParameter("course_id"));
        int moduleId = Integer.parseInt(req.getParameter("module_id"));

        try {
            if (!enrollmentDao.isEnrolled(courseId, currentUser.getId())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            int enrollmentId = enrollmentDao.findEnrollmentId(courseId, currentUser.getId());
            progressDao.markComplete(enrollmentId, moduleId);
            resp.sendRedirect(req.getContextPath() + "/courses/view?course_id=" + courseId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
