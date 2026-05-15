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

@WebServlet(name = "CreateCommunityServlet", urlPatterns = { "/creator/create-community" })
public class CreateCommunityServlet extends HttpServlet {
    private CommunityDao dao = new CommunityDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/creator/create-community.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        Community c = new Community();
        c.setName(name);
        c.setDescription(description);
        c.setCreatorId(u.getId());
        try {
            int id = dao.create(c);
            resp.sendRedirect(req.getContextPath() + "/communities");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
