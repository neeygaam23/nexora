package controller;

import dao.CommunityDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/manage-communities")
public class AdminManageCommunitiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommunityDao communityDao = new CommunityDao();
        try {
            req.setAttribute("communities", communityDao.listAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/manage-communities.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to load communities", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String communityId = req.getParameter("communityId");

        CommunityDao communityDao = new CommunityDao();
        try {
            if ("delete".equalsIgnoreCase(action)) {
                communityDao.delete(Integer.parseInt(communityId));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/manage-communities");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Failed to manage community", e);
        }
    }
}
