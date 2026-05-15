package controller;

import model.User;
import service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Basic AuthServlet handling login requests. Uses session for authentication.
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            User user = authService.authenticate(username, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("currentUser", user);
                String role = user.getRoleName();
                if ("ADMIN".equalsIgnoreCase(role)) {
                    resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                } else if ("CREATOR".equalsIgnoreCase(role)) {
                    resp.sendRedirect(req.getContextPath() + "/creator/dashboard");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/member/dashboard");
                }
            } else {
                req.setAttribute("errors", java.util.Collections.singletonList("Invalid credentials"));
                req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
