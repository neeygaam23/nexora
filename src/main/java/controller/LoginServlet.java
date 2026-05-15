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
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Handles login GET/POST. On POST, validates credentials and manages session.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        List<String> errors = ValidationUtil.validateLogin(username, password);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
            return;
        }

        try {
            Optional<User> opt = UserDao.findByUsername(username);
            if (opt.isPresent()) {
                User user = opt.get();
                boolean ok = PasswordUtil.verifyPassword(password, user.getPasswordHash());
                if (ok) {
                    HttpSession session = req.getSession();
                    // Do not store password hash in session
                    user.setPasswordHash(null);
                    session.setAttribute("currentUser", user);

                    // Role-based redirect
                    String role = user.getRoleName();
                    if ("ADMIN".equalsIgnoreCase(role)) {
                        resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                    } else if ("CREATOR".equalsIgnoreCase(role)) {
                        resp.sendRedirect(req.getContextPath() + "/creator/dashboard");
                    } else {
                        resp.sendRedirect(req.getContextPath() + "/member/dashboard");
                    }
                    return;
                }
            }
            // invalid credentials
            req.setAttribute("errors", java.util.Collections.singletonList("Invalid username or password."));
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
