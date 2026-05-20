package controller;

import dao.CourseMaterialDao;
import dao.CourseDao;
import dao.EnrollmentDao;
import model.Course;
import model.User;
import util.UploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

@WebServlet("/materials/download")
public class MaterialDownloadServlet extends HttpServlet {
    private CourseDao courseDao = new CourseDao();
    private EnrollmentDao enrollmentDao = new EnrollmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        // fetch material row
        try (Connection c = util.DBConnectionUtil.getConnection();
                PreparedStatement ps = c.prepareStatement("SELECT * FROM course_materials WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                int courseId = rs.getInt("course_id");
                String filename = rs.getString("filename");
                String stored = rs.getString("stored_path");
                String contentType = getOptionalString(rs, "content_type");
                String mediaType = getOptionalString(rs, "media_type");

                User current = (User) req.getSession().getAttribute("currentUser");
                boolean allowed = false;
                if (current != null) {
                    Course course = courseDao.findById(courseId);
                    if (course != null) {
                        if (current.getId() == course.getCreatorId())
                            allowed = true;
                        else if (enrollmentDao.isEnrolled(courseId, current.getId()))
                            allowed = true;
                    }
                }
                if (!allowed) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                File f = new File(stored);
                if (!f.isAbsolute()) {
                    f = new File(UploadUtil.getUploadsDir(), stored);
                }
                if (!f.exists()) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                boolean inline = "true".equalsIgnoreCase(req.getParameter("inline"));
                if ((contentType == null || contentType.isBlank()) && "video".equalsIgnoreCase(mediaType)) {
                    contentType = guessContentType(filename);
                }
                if (contentType != null && !contentType.isBlank()) {
                    resp.setContentType(contentType);
                }
                resp.setHeader("Content-Disposition",
                        (inline ? "inline" : "attachment") + "; filename=\"" + filename + "\"");
                resp.setContentLengthLong(f.length());
                try (FileInputStream in = new FileInputStream(f)) {
                    in.transferTo(resp.getOutputStream());
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private String getOptionalString(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (SQLException ignored) {
            return null;
        }
    }

    private String guessContentType(String filename) {
        String lower = filename == null ? "" : filename.toLowerCase();
        if (lower.endsWith(".mp4"))
            return "video/mp4";
        if (lower.endsWith(".webm"))
            return "video/webm";
        if (lower.endsWith(".ogg"))
            return "video/ogg";
        if (lower.endsWith(".mov"))
            return "video/quicktime";
        if (lower.endsWith(".mkv"))
            return "video/x-matroska";
        return "application/octet-stream";
    }
}
