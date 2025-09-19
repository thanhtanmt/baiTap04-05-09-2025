package webstar.service;

import java.util.List;

import webstar.model.Category;
import webstar.model.User;

public interface UserService {
	User login(String username, String password);

	User findByUserName(String username);

	void insert(User user);

	void resetPassword(String email, String newPassword);

	void createCategory(Long category_id, String name, String description, String user_id);

	List<Category> readCategory(String user_id);

	void updateCategory(String category_id, String name, String description, String user_id);

	void deleteCategory(String catagory_id);

	String getID(String username);

	boolean register(String username, String password, String email, String fullname, String phone);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	boolean checkExistPhone(String phone);
}
