package webstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import webstar.model.User;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/waiting")
public class WaitingController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
    	String alertMsg="";
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            User u = (User) session.getAttribute("account");
            req.setAttribute("username", u.getUserName());

            if (u.getRoleid() == 1) {
                resp.sendRedirect(req.getContextPath() + "/home/user");
            } else if (u.getRoleid() == 2) {
                resp.sendRedirect(req.getContextPath() + "/home/manager");
            } else if(u.getRoleid() == 3) {
            	resp.sendRedirect(req.getContextPath() + "/home/admin");
            }else {
            	alertMsg = "Tài khoản này không hợp lệ";
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
