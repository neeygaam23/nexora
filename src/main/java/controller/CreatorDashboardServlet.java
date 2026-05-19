package controller;

import dao.CommunityDao;
import dao.CourseDao;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/creator/dashboard")
public class CreatorDashboardServlet extends HttpServlet {
    private final CommunityDao communityDao = new CommunityDao();
    private final CourseDao courseDao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();

        try {
            req.setAttribute("communities", communityDao.listByCreator(userId));
            req.setAttribute("courses", courseDao.listByCreator(userId));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        req.getRequestDispatcher("/WEB-INF/views/creator/creator-dashboard.jsp").forward(req, resp);
    }
}