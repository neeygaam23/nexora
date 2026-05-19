package controller;

import dao.CommunityDao;
import dao.CourseDao;
import model.Community;
import model.Course;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/courses/browse")
public class BrowseCoursesServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();
    private final CommunityDao communityDao = new CommunityDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Course> courses = courseDao.listAll();
            Map<Integer, String> communityNames = new HashMap<>();
            for (Course course : courses) {
                if (!communityNames.containsKey(course.getCommunityId())) {
                    Community community = communityDao.findById(course.getCommunityId());
                    communityNames.put(course.getCommunityId(),
                            community != null ? community.getName() : "Unknown community");
                }
            }

            req.setAttribute("courses", courses);
            req.setAttribute("communityNames", communityNames);
            req.getRequestDispatcher("/WEB-INF/views/courses/browse-courses.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}