package controller;

import dao.CourseDao;
import dao.EnrollmentDao;
import model.Course;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/courses/purchase")
public class CoursePurchaseServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User current = (User) req.getSession().getAttribute("currentUser");
        if (current == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int courseId;
        try {
            courseId = Integer.parseInt(req.getParameter("course_id"));
        } catch (Exception ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing course_id");
            return;
        }

        try {
            Course course = courseDao.findById(courseId);
            if (course == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if (current.getId() == course.getCreatorId() || enrollmentDao.isEnrolled(courseId, current.getId())) {
                resp.sendRedirect(req.getContextPath() + "/courses/view?course_id=" + courseId);
                return;
            }

            req.setAttribute("course", course);
            req.getRequestDispatcher("/WEB-INF/views/courses/purchase-course.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User current = (User) req.getSession().getAttribute("currentUser");
        if (current == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int courseId;
        try {
            courseId = Integer.parseInt(req.getParameter("course_id"));
        } catch (Exception ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing course_id");
            return;
        }

        try {
            Course course = courseDao.findById(courseId);
            if (course == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if (current.getId() == course.getCreatorId() || enrollmentDao.isEnrolled(courseId, current.getId())) {
                resp.sendRedirect(req.getContextPath() + "/courses/view?course_id=" + courseId);
                return;
            }

            if (course.isPaid()) {
                enrollmentDao.enroll(courseId, current.getId(), "paid", course.getPrice());
            } else {
                enrollmentDao.enroll(courseId, current.getId());
            }

            resp.sendRedirect(req.getContextPath() + "/courses/view?course_id=" + courseId + "&payment=success");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}