package controller;

import dao.CommentDao;
import dao.PostDao;
import dao.PostReactionDao;
import model.Comment;
import model.Post;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/communities/posts")
public class PostServlet extends HttpServlet {
    private final PostDao postDao = new PostDao();
    private final CommentDao commentDao = new CommentDao();
    private final PostReactionDao reactionDao = new PostReactionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int communityId = Integer.parseInt(req.getParameter("community_id"));
        User currentUser = (User) req.getSession().getAttribute("currentUser");

        try {
            List<Post> posts = postDao.listByCommunity(communityId);
            Map<Integer, List<Comment>> commentsMap = new HashMap<>();
            Map<Integer, Integer> reactionCountsMap = new HashMap<>();
            Map<Integer, Boolean> userLikedMap = new HashMap<>();
            for (Post post : posts) {
                commentsMap.put(post.getId(), commentDao.listByPost(post.getId()));
                reactionCountsMap.put(post.getId(), reactionDao.countLikes(post.getId()));
                userLikedMap.put(post.getId(),
                        currentUser != null && reactionDao.hasLiked(post.getId(), currentUser.getId()));
            }

            req.setAttribute("posts", posts);
            req.setAttribute("commentsMap", commentsMap);
            req.setAttribute("reactionCountsMap", reactionCountsMap);
            req.setAttribute("userLikedMap", userLikedMap);
            req.setAttribute("communityId", communityId);
            req.getRequestDispatcher("/WEB-INF/views/communities/posts.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        int communityId = Integer.parseInt(req.getParameter("community_id"));

        Post post = new Post();
        post.setCommunityId(communityId);
        post.setTitle(req.getParameter("title"));
        post.setBody(req.getParameter("body"));
        post.setAuthorId(currentUser.getId());

        try {
            postDao.create(post);
            resp.sendRedirect(req.getContextPath() + "/communities/posts?community_id=" + communityId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}