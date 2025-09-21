package webstar.filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import webstar.model.User;

@WebFilter("/home/*")
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("account") : null;

        String uri = req.getRequestURI();

        if (user == null && !uri.endsWith("login") && !uri.contains("css") && !uri.contains("js")) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (uri.startsWith(req.getContextPath() + "/home/admin") && user.getRoleid() != 3) {
            resp.sendRedirect(req.getContextPath() + "/accessDenied.jsp");
            return;
        }

        if (uri.startsWith(req.getContextPath() + "/home/manager") && user.getRoleid() != 2) {
            resp.sendRedirect(req.getContextPath() + "/accessDenied.jsp");
            return;
        }
        if (uri.startsWith(req.getContextPath() + "/home/user") && user.getRoleid() != 1) {
            resp.sendRedirect(req.getContextPath() + "/accessDenied.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
         
    }
}
