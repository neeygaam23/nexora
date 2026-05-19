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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/member/enrollments")
public class MyEnrollmentsServlet extends HttpServlet {
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();
    private final CourseDao courseDao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();

        try {
            List<Integer> courseIds = enrollmentDao.listCourseIdsByUser(userId);
            List<Course> enrolledCourses = new ArrayList<>();

            for (Integer courseId : courseIds) {
                Course course = courseDao.findById(courseId);
                if (course != null) {
                    enrolledCourses.add(course);
                }
            }

            req.setAttribute("enrolledCourses", enrolledCourses);
            req.getRequestDispatcher("/WEB-INF/views/member/enrollments.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}