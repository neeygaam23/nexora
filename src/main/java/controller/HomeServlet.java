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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = { "/home", "/" })
public class HomeServlet extends HttpServlet {
    private final CommunityDao communityDao = new CommunityDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        try {
            List<Community> communities = communityDao.listAll();
            req.setAttribute("featuredCommunities", limitCommunities(communities, 6));
            req.setAttribute("communityCount", communities.size());
            req.setAttribute("loggedIn", currentUser != null);
            req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private List<Community> limitCommunities(List<Community> communities, int maxItems) {
        if (communities == null || communities.size() <= maxItems) {
            return communities;
        }
        return new ArrayList<>(communities.subList(0, maxItems));
    }
}