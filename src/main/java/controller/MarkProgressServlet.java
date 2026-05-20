package controller;

import dao.EnrollmentDao;
import dao.ProgressDao;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Handles marking a course module as completed.
 * Ensures the user is enrolled before updating progress.
 */
@WebServlet("/courses/progress")
public class MarkProgressServlet extends HttpServlet {

    // DAO instances for database operations
    private final EnrollmentDao enrollmentDao = new EnrollmentDao();
    private final ProgressDao progressDao = new ProgressDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get currently logged-in user from session
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        // Redirect to login if user is not authenticated
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Parse request parameters
        int courseId = Integer.parseInt(request.getParameter("course_id"));
        int moduleId = Integer.parseInt(request.getParameter("module_id"));

        try {
            // Check if user is enrolled in this course
            boolean enrolled = enrollmentDao.isEnrolled(courseId, currentUser.getId());

            if (!enrolled) {
                // Prevent unauthorized progress updates
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // Get enrollment ID for progress tracking
            int enrollmentId = enrollmentDao.findEnrollmentId(courseId, currentUser.getId());

            // Mark module as completed
            progressDao.markComplete(enrollmentId, moduleId);

            // Redirect back to course view page
            response.sendRedirect(
                    request.getContextPath()
                            + "/courses/view?course_id=" + courseId
            );

        } catch (SQLException e) {
            // Wrap database error into servlet exception
            throw new ServletException("Error while updating course progress", e);
        }
    }
}