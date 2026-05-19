package controller;

import dao.UserDao;
import model.User;
import util.PasswordUtil;
import util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Handles user registration. Simple server-side validation, password hashing,
 * and user creation.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirmPassword");
        String fullName = req.getParameter("fullName");

        List<String> errors = ValidationUtil.validateRegistration(username, email, password, confirm);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
            return;
        }

        String chosenRole = req.getParameter("role");
        if (chosenRole == null || (!chosenRole.equalsIgnoreCase("MEMBER") && !chosenRole.equalsIgnoreCase("CREATOR"))) {
            req.setAttribute("errors", List.of("Please select a valid role: Member or Creator."));
            req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
            return;
        }

        try {
            Optional<User> byUsername = userDao.findByUsername(username);
            if (byUsername.isPresent()) {
                req.setAttribute("errors", List.of("Username is already taken."));
                req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
                return;
            }
            Optional<User> byEmail = userDao.findByEmail(email);
            if (byEmail.isPresent()) {
                req.setAttribute("errors", List.of("An account with this email already exists."));
                req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
                return;
            }

            // Hash password (salt:hash stored)
            String stored = PasswordUtil.createStoredPassword(password);

            User u = new User();
            u.setUsername(username);
            u.setEmail(email);
            u.setPasswordHash(stored);
            u.setFullName(fullName);
            int roleId = 3; // default MEMBER
            if ("CREATOR".equalsIgnoreCase(chosenRole)) {
                roleId = 2;
            }
            u.setRoleId(roleId);

            int newId = userDao.createUser(u);
            if (newId > 0) {
                resp.sendRedirect(req.getContextPath() + "/login?registered=1");
            } else {
                req.setAttribute("errors", List.of("Unable to create account. Please try again."));
                req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
