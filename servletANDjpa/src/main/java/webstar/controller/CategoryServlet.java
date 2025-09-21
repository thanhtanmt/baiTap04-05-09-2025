package webstar.controller;

import webstar.service.UserService;
import webstar.service.impl.UserServiceImpl;
import webstar.model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import webstar.model.User;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService categoryDao = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy user_id từ session (khi login bạn nên lưu userId vào session)
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        User u = (User) session.getAttribute("accout");
        int roleId = u.getRoleid();
        String path = (roleId == 1) ? "/home/user" :
        			  (roleId == 2) ? "/home/manager" :
        				  			  "/home/admin";
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        // Lấy danh sách category của user
        List<Category> categories = categoryDao.readCategory(userId);
        request.setAttribute("category", categories);
        request.getRequestDispatcher(path).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        User u = (User) session.getAttribute("account");
        int roleId = u.getRoleid();
        String path = (roleId == 1) ? "/views/user_index.jsp" :
        			  (roleId == 2) ? "/views/manager_index.jsp" :
        				  			  "/views/admin_index.jsp";
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + path);
            return;
        }

        try {
            switch (action) {
                case "create":
                    String name = request.getParameter("name");
                    String description = request.getParameter("description");
                    categoryDao.createCategory(null, name, description, userId);
                    request.setAttribute("message", "Thêm category thành công!");
                    break;

                case "update":
                    String catId = request.getParameter("categoryId");
                    String newName = request.getParameter("name");
                    String newDesc = request.getParameter("description");
                    String userid_real= request.getParameter("userid");
                    categoryDao.updateCategory(catId, newName, newDesc, userid_real);
                    request.setAttribute("message", "Cập nhật category thành công!");
                    break;

                case "delete":
                    String delId = request.getParameter("categoryId");
                    categoryDao.deleteCategory(delId);
                    request.setAttribute("message", "Xóa category thành công!");
                    break;
                default:
                    request.setAttribute("message", "Hành động không hợp lệ!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Sau khi xử lý xong, load lại danh sách
        List<Category> categories = categoryDao.readCategory(userId);
        request.setAttribute("category", categories);
        request.getRequestDispatcher(path).forward(request, response);
    }
}
