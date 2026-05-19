package controller;

import dao.CommunityDao;
import model.Community;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/creator/delete-community")
public class DeleteCommunityServlet extends HttpServlet {
    private final CommunityDao communityDao = new CommunityDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        int communityId;
        try {
            communityId = Integer.parseInt(req.getParameter("community_id"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Community community = communityDao.findById(communityId);
            if (community == null || community.getCreatorId() != currentUser.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            communityDao.delete(communityId);
            resp.sendRedirect(req.getContextPath() + "/creator/dashboard");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
