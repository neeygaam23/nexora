package controller;

import dao.CommunityDao;
import dao.CourseDao;
import dao.CommunityMemberDao;
import model.Community;
import model.Course;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/communities/view")
public class CommunityViewServlet extends HttpServlet {
    private CommunityDao communityDao = new CommunityDao();
    private CourseDao courseDao = new CourseDao();
    private CommunityMemberDao memberDao = new CommunityMemberDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int communityId = Integer.parseInt(req.getParameter("community_id"));
        try {
            Community community = communityDao.findById(communityId);
            if (community == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            List<Course> courses = courseDao.listByCommunity(communityId);
            User current = (User) req.getSession().getAttribute("currentUser");
            boolean isMember = false;
            if (current != null)
                isMember = memberDao.isMember(communityId, current.getId());

            req.setAttribute("community", community);
            req.setAttribute("courses", courses);
            req.setAttribute("isMember", isMember);
            req.getRequestDispatcher("/WEB-INF/views/communities/community.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
