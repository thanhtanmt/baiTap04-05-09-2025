package webstar.service.impl;

import java.util.List;

import webstar.dao.UserDao;
import webstar.dao.impl.UserDaoImpl;
import webstar.model.Category;
import webstar.model.User;
import webstar.service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = userDao.findByUserName(username);
        if (user != null && password.equals(user.getPassWord())) {
            return user;
        }
        return null;
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }
    
    @Override
    public void resetPassword(String email, String newPassword) {
        userDao.updatePassword(email, newPassword);
    }
    
    @Override
    public void createCategory(Long category_id, String name, String description, String user_id) {
    	userDao.createCategory(category_id, name, description, user_id);
    }
    
    @Override
    public List<Category> readCategory(String user_id) {
    	return userDao.readCategory(user_id);
    }
    
    @Override
    public void  updateCategory(String category_id, String name, String description, String user_id) {
    	userDao.updateCategory(category_id, name, description, user_id);
    }
    
    @Override
    public void deleteCategory(String catagory_id) {
    	userDao.deleteCategory(catagory_id);
    }
    
    @Override
    public String getID(String username) {
    	return userDao.getID(username);
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        if (userDao.checkExistUsername(username)) {
            return false;
        }
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        userDao.insert(new User(email, username, fullname, password, null, 1, phone, date));
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }
}
