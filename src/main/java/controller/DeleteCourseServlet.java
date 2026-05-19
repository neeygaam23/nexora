package controller;

import dao.CourseDao;
import model.Course;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/creator/delete-course")
public class DeleteCourseServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        int courseId;
        try {
            courseId = Integer.parseInt(req.getParameter("course_id"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Course course = courseDao.findById(courseId);
            if (course == null || course.getCreatorId() != currentUser.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            courseDao.delete(courseId);
            resp.sendRedirect(req.getContextPath() + "/creator/dashboard");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
