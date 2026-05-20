package controller;

import dao.CommunityDao;
import dao.CourseDao;
import dao.EnrollmentDao;
import model.Community;
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

@WebServlet("/member/dashboard")
public class MemberDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        try {
            List<Community> communities = new CommunityDao().listAll();
            req.setAttribute("communities", communities);

            EnrollmentDao enrollmentDao = new EnrollmentDao();
            CourseDao courseDao = new CourseDao();
            List<Course> enrolledCourses = new ArrayList<>();
            for (Integer courseId : enrollmentDao.listCourseIdsByUser(currentUser.getId())) {
                Course course = courseDao.findById(courseId);
                if (course != null) {
                    enrolledCourses.add(course);
                }
            }
            req.setAttribute("enrolledCourses", enrolledCourses);

            req.getRequestDispatcher("/WEB-INF/views/member/member-dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}