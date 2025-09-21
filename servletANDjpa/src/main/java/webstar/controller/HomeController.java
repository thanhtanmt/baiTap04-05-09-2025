package webstar.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/home/admin", "/home/user", "/home/manager" })
public class HomeController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String alertMsg = "";
		String path = req.getRequestURI();
		HttpSession session = req.getSession(false);
		if (session != null) {
			if (path.startsWith(req.getContextPath() + "/home/user")) {
				req.getRequestDispatcher("/views/user_index.jsp").forward(req, resp);
			} else if (path.startsWith(req.getContextPath() + "/home/manager")) {
				req.getRequestDispatcher("/views/manager_index.jsp").forward(req, resp);
			} else if (path.startsWith(req.getContextPath() + "/home/admin")) {
				req.getRequestDispatcher("/views/admin_index.jsp").forward(req, resp);
			} else {
				alertMsg = "Lỗi url";
				req.setAttribute("alert", alertMsg);
				req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			}
		} else {
			alertMsg = "Lỗi session";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
		}
	}

}
