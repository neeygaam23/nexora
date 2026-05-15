package filter;

import model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * AuthFilter intercepts requests to enforce authentication and simple
 * role-based access.
 * - Allows public resources: /login, /register, /access-denied, /assets/*
 * - Protects other URLs and redirects to /login if not authenticated
 * - Redirects to access-denied for unauthorized role paths
 */
@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Allow static resources and public auth pages
        if (path.startsWith("/assets/") || path.startsWith("/login") || path.startsWith("/register")
                || path.startsWith("/access-denied")
                || path.startsWith("/css/") || path.startsWith("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        User current = (session != null) ? (User) session.getAttribute("currentUser") : null;
        if (current == null) {
            // Not logged in: redirect to login page
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Simple role-based URL access control
        String role = current.getRoleName();
        if (path.startsWith("/admin") && !"ADMIN".equalsIgnoreCase(role)) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }
        if (path.startsWith("/creator") && !("CREATOR".equalsIgnoreCase(role) || "ADMIN".equalsIgnoreCase(role))) {
            resp.sendRedirect(req.getContextPath() + "/access-denied");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
