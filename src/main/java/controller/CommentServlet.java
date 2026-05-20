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

/**
 * Handles creation of comments on posts.
 * Receives comment data from form and stores it in database.
 */
@WebServlet("/posts/comment")
public class CommentServlet extends HttpServlet {

    // DAO for comment database operations
    private final CommentDao commentDao = new CommentDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get logged-in user from session
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        // Read request parameters
        String postIdParam = request.getParameter("post_id");
        String communityIdParam = request.getParameter("community_id");

        /**
         * Basic validation:
         * Ensures required identifiers are present and not invalid strings.
         */
        if (postIdParam == null || postIdParam.isBlank()
                || "null".equalsIgnoreCase(postIdParam)
                || communityIdParam == null || communityIdParam.isBlank()
                || "null".equalsIgnoreCase(communityIdParam)) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Missing post or community identifier"
            );
            return;
        }

        // Convert post ID safely (assuming valid numeric input after validation)
        int postId = Integer.parseInt(postIdParam);

        // Build Comment object
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setAuthorId(currentUser.getId());
        comment.setBody(request.getParameter("body"));

        try {
            // Save comment to database
            commentDao.create(comment);

            // Redirect back to community posts page
            response.sendRedirect(
                    request.getContextPath()
                            + "/communities/posts?community_id="
                            + communityIdParam
            );

        } catch (SQLException e) {
            // Wrap SQL error for servlet container handling
            throw new ServletException("Error while saving comment", e);
        }
    }
}