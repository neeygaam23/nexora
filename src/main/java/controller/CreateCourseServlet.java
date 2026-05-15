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

@WebServlet(name = "CreateCourseServlet", urlPatterns = { "/creator/create-course" })
public class CreateCourseServlet extends HttpServlet {
    private CourseDao dao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/creator/create-course.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int communityId = 0;
        try {
            communityId = Integer.parseInt(req.getParameter("community_id"));
        } catch (Exception ignored) {
        }

        Course c = new Course();
        c.setCommunityId(communityId);
        c.setTitle(title);
        c.setDescription(description);
        c.setCreatorId(u.getId());
        try {
            dao.create(c);
            resp.sendRedirect(req.getContextPath() + "/communities");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
