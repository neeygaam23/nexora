package controller;

import dao.CourseDao;
import dao.CourseMaterialDao;
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
import java.util.UUID;

@WebServlet("/creator/upload-material")
@MultipartConfig(maxFileSize = 524288000L, maxRequestSize = 524288000L)
public class UploadMaterialServlet extends HttpServlet {
    private final CourseMaterialDao materialDao = new CourseMaterialDao();
    private final CourseDao courseDao = new CourseDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int courseId = Integer.parseInt(req.getParameter("course_id"));
        try {
            var course = courseDao.findById(courseId);
            if (course == null || course.getCreatorId() != u.getId()) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            Part filePart = req.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                resp.sendRedirect(req.getContextPath() + "/courses/view?course_id=" + courseId);
                return;
            }

            File dir = UploadUtil.getUploadsDir();
            String submitted = filePart.getSubmittedFileName();
            String safeSubmitted = submitted == null ? "material.bin" : Paths.get(submitted).getFileName().toString();
            String extension = "";
            int dot = safeSubmitted.lastIndexOf('.');
            if (dot >= 0) {
                extension = safeSubmitted.substring(dot);
            }
            String storedName = Instant.now().toEpochMilli() + "_" + UUID.randomUUID() + extension;
            File storedFile = new File(dir, storedName);
            try (InputStream in = filePart.getInputStream()) {
                Files.copy(in, storedFile.toPath());
            }

            String contentType = filePart.getContentType();
            String mediaType = isVideo(contentType, safeSubmitted) ? "video" : "file";
            materialDao.create(courseId, u.getId(), safeSubmitted, storedFile.getAbsolutePath(), contentType,
                    mediaType);
            resp.sendRedirect(req.getContextPath() + "/courses/view?course_id=" + courseId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private boolean isVideo(String contentType, String filename) {
        if (contentType != null && contentType.toLowerCase().startsWith("video/")) {
            return true;
        }
        String lower = filename == null ? "" : filename.toLowerCase();
        return lower.endsWith(".mp4") || lower.endsWith(".webm") || lower.endsWith(".ogg")
                || lower.endsWith(".mov") || lower.endsWith(".m4v") || lower.endsWith(".mkv");
    }
}
