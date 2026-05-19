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

@WebServlet(name = "CommunityListServlet", urlPatterns = { "/communities" })
public class CommunityListServlet extends HttpServlet {
    private CommunityDao dao = new CommunityDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Community> list = dao.listAll();
            req.setAttribute("communities", list);
            req.getRequestDispatcher("/WEB-INF/views/communities/browse-communities.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
