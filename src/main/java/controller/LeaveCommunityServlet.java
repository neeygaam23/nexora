package controller;

import dao.CommunityMemberDao;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/communities/leave")
public class LeaveCommunityServlet extends HttpServlet {
    private CommunityMemberDao dao = new CommunityMemberDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int communityId = Integer.parseInt(req.getParameter("community_id"));
        try {
            dao.leave(communityId, u.getId());
            resp.sendRedirect(req.getContextPath() + "/communities");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
