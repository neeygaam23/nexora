package controller;

import dao.CommentDao;
import model.Comment;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/posts/comment")
public class CommentServlet extends HttpServlet {
    private final CommentDao commentDao = new CommentDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        String postIdParam = req.getParameter("post_id");
        String communityIdParam = req.getParameter("community_id");

        if (postIdParam == null || postIdParam.isBlank() || "null".equalsIgnoreCase(postIdParam)
                || communityIdParam == null || communityIdParam.isBlank()
                || "null".equalsIgnoreCase(communityIdParam)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing post or community identifier");
            return;
        }

        int postId = Integer.parseInt(postIdParam);

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setAuthorId(currentUser.getId());
        comment.setBody(req.getParameter("body"));

        try {
            commentDao.create(comment);
            resp.sendRedirect(req.getContextPath() + "/communities/posts?community_id=" + communityIdParam);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}