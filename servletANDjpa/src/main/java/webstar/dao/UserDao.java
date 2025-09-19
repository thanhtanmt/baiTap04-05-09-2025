package webstar.dao;

import webstar.model.Category;
import webstar.model.User;
import java.util.List;

public interface UserDao {
    User findByUserName(String username);

    void insert(User user);
    
    void updatePassword(String email, String newPassword);
    
    void createCategory(Long category_id, String name, String description, String user_id);
    
    List<Category> readCategory(String user_id);
    
    void  updateCategory(String category_id, String name, String description, String user_id);
    
    void deleteCategory(String catagory_id);
    
    String getID(String username);

    boolean checkExistEmail(String email);

    boolean checkExistUsername(String username);

    boolean checkExistPhone(String phone);
}
