package controller;

import dao.CourseDao;
import dao.CourseMaterialDao;
import dao.CommunityDao;
import model.Course;
import model.Community;
import model.User;
import util.UploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "CreateCourseServlet", urlPatterns = { "/creator/create-course" })
@MultipartConfig(maxFileSize = 524288000L, maxRequestSize = 524288000L)
public class CreateCourseServlet extends HttpServlet {
    private CourseDao dao = new CourseDao();
    private final CommunityDao communityDao = new CommunityDao();
    private final CourseMaterialDao materialDao = new CourseMaterialDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            List<Community> communities = "ADMIN".equalsIgnoreCase(u.getRoleName()) ? communityDao.listAll()
                    : communityDao.listByCreator(u.getId());
            req.setAttribute("communities", communities);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        req.getRequestDispatcher("/WEB-INF/views/creator/create-course.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (!"CREATOR".equalsIgnoreCase(u.getRoleName()) &&
                !"ADMIN".equalsIgnoreCase(u.getRoleName())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int communityId;
        try {
            communityId = Integer.parseInt(req.getParameter("community_id"));
        } catch (Exception ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please choose a community");
            return;
        }

        try {
            Community selectedCommunity = communityDao.findById(communityId);
            if (selectedCommunity == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Selected community does not exist");
                return;
            }
            if (!"ADMIN".equalsIgnoreCase(u.getRoleName()) && selectedCommunity.getCreatorId() != u.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            Course c = new Course();
            c.setCommunityId(communityId);
            c.setTitle(title);
            c.setDescription(description);
            c.setCreatorId(u.getId());
            int courseId = dao.create(c);

            Part videoPart = req.getPart("video_file");
            if (videoPart != null && videoPart.getSize() > 0) {
                String submitted = videoPart.getSubmittedFileName();
                String safeSubmitted = submitted == null ? "course-video.mp4"
                        : Paths.get(submitted).getFileName().toString();
                String extension = "";
                int dot = safeSubmitted.lastIndexOf('.');
                if (dot >= 0) {
                    extension = safeSubmitted.substring(dot);
                }
                String storedName = Instant.now().toEpochMilli() + "_" + UUID.randomUUID() + extension;
                File dir = UploadUtil.getUploadsDir();
                File storedFile = new File(dir, storedName);
                try (InputStream in = videoPart.getInputStream()) {
                    Files.copy(in, storedFile.toPath());
                }
                materialDao.create(courseId, u.getId(), safeSubmitted, storedFile.getAbsolutePath(),
                        videoPart.getContentType(), "video");
            }

            resp.sendRedirect(req.getContextPath() + "/courses/view?course_id=" + courseId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
