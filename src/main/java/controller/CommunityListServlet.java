package controller;

import dao.CommunityDao;
import model.Community;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet responsible for displaying all available communities.
 * It fetches data from the database and forwards it to the browse page.
 */
@WebServlet(name = "CommunityListServlet", urlPatterns = {"/communities"})
public class CommunityListServlet extends HttpServlet {

    // DAO instance to handle community database operations
    private final CommunityDao communityDao = new CommunityDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Fetch all communities from database
            List<Community> communities = communityDao.listAll();

            // Store data in request scope for JSP rendering
            request.setAttribute("communities", communities);

            // Forward request to JSP page for UI display
            request.getRequestDispatcher(
                    "/WEB-INF/views/communities/browse-communities.jsp"
            ).forward(request, response);

        } catch (SQLException e) {
            // Convert SQL exception into servlet exception for proper handling
            throw new ServletException("Error while loading communities list", e);
        }
    }
}