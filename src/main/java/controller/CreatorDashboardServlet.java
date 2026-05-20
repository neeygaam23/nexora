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

/**
 * Handles the Creator Dashboard page.
 * This servlet fetches all communities and courses
 * created by the currently logged-in user.
 */
@WebServlet("/creator/dashboard")
public class CreatorDashboardServlet extends HttpServlet {

    // DAO objects to interact with database
    private final CommunityDao communityDao = new CommunityDao();
    private final CourseDao courseDao = new CourseDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the currently logged-in user from session
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        // Extract user ID (used for fetching creator-specific data)
        int userId = currentUser.getId();

        try {
            // Fetch all communities created by this user
            request.setAttribute("communities",
                    communityDao.listByCreator(userId));

            // Fetch all courses created by this user
            request.setAttribute("courses",
                    courseDao.listByCreator(userId));

        } catch (SQLException e) {
            // Wrap SQL exception into ServletException for proper handling
            throw new ServletException("Error loading creator dashboard data", e);
        }

        // Forward data to JSP view for rendering dashboard UI
        request.getRequestDispatcher(
                "/WEB-INF/views/creator/creator-dashboard.jsp"
        ).forward(request, response);
    }
}