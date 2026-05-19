package controller;

import dao.PostReactionDao;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/posts/reaction")
public class PostReactionServlet extends HttpServlet {
    private final PostReactionDao reactionDao = new PostReactionDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        String postIdParam = req.getParameter("post_id");
        String communityIdParam = req.getParameter("community_id");

        if (postIdParam == null || postIdParam.isBlank() || communityIdParam == null || communityIdParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing post or community identifier");
            return;
        }

        int postId = Integer.parseInt(postIdParam);
        try {
            reactionDao.toggleLike(postId, currentUser.getId());
            resp.sendRedirect(req.getContextPath() + "/communities/posts?community_id=" + communityIdParam);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}