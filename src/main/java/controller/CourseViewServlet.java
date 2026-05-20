package controller;

import dao.CourseDao;
import dao.CourseMaterialDao;
import dao.CommunityMemberDao;
import dao.EnrollmentDao;
import dao.FollowDao;
import dao.ProgressDao;
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

@WebServlet("/courses/view")
public class CourseViewServlet extends HttpServlet {
    private CourseDao courseDao = new CourseDao();
    private CourseMaterialDao materialDao = new CourseMaterialDao();
    private CommunityMemberDao memberDao = new CommunityMemberDao();
    private EnrollmentDao enrollmentDao = new EnrollmentDao();
    private FollowDao followDao = new FollowDao();
    private ProgressDao progressDao = new ProgressDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int courseId = Integer.parseInt(req.getParameter("course_id"));
        try {
            Course course = courseDao.findById(courseId);
            if (course == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            User current = (User) req.getSession().getAttribute("currentUser");
            boolean allowed = false;
            boolean isEnrolled = false;
            if (current != null) {
                if (current.getId() == course.getCreatorId())
                    allowed = true;
                else if (enrollmentDao.isEnrolled(courseId, current.getId()))
                    allowed = true;
                else if (memberDao.isMember(course.getCommunityId(), current.getId()))
                    allowed = true;
                else if (followDao.isFollowing(current.getId(), course.getCreatorId()))
                    allowed = true;
                isEnrolled = enrollmentDao.isEnrolled(courseId, current.getId());
            }
            if (!allowed) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            List<String[]> materials = materialDao.listByCourse(courseId);
            req.setAttribute("course", course);
            req.setAttribute("materials", materials);
            req.setAttribute("totalMaterials", materials.size());
            boolean canUpload = current != null && current.getId() == course.getCreatorId();
            req.setAttribute("canUpload", canUpload);
            req.setAttribute("isEnrolled", isEnrolled);
            boolean canAccessMaterials = current != null && (current.getId() == course.getCreatorId() || isEnrolled);
            req.setAttribute("canAccessMaterials", canAccessMaterials);
            boolean canPurchase = current != null && course.isPaid() && !isEnrolled
                    && current.getId() != course.getCreatorId();
            req.setAttribute("canPurchase", canPurchase);
            req.setAttribute("isPaidCourse", course.isPaid());
            if ("success".equalsIgnoreCase(req.getParameter("payment"))) {
                req.setAttribute("purchaseSuccess", "eSewa payment simulated successfully.");
            }
            if (isEnrolled) {
                int enrollmentId = enrollmentDao.findEnrollmentId(courseId, current.getId());
                req.setAttribute("completedCount", progressDao.countCompleted(enrollmentId));
            } else {
                req.setAttribute("completedCount", 0);
            }
            req.getRequestDispatcher("/WEB-INF/views/courses/view-course.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
