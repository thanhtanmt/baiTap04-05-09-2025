package webstar.controller;

import webstar.service.UserService;
import webstar.service.impl.UserServiceImpl;
import webstar.model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

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
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        // Lấy danh sách category của user
        List<Category> categories = categoryDao.readCategory(userId);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
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
                    categoryDao.updateCategory(catId, newName, newDesc, userId);
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
        request.getRequestDispatcher("views/home.jsp").forward(request, response);
    }
}
