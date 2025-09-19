package webstar.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

import webstar.dao.UserDao;
import webstar.model.User;
import webstar.model.Category;

public class UserDaoImpl implements UserDao {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("webstarPU");

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	@Override
	public User findByUserName(String username) {
		EntityManager em = getEntityManager();
		try {
			return em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class)
					.setParameter("username", username).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	@Override
	public void insert(User user) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(user);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void updatePassword(String email, String newPassword) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
					.setParameter("email", email).getSingleResult();
			if (user != null) {
				user.setPassWord(newPassword);
				em.merge(user);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void createCategory(Long category_id, String name, String description, String user_id) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Category category = new Category();
			category.setCategoryId(category_id);
			category.setName(name);
			category.setDescription(description);
			category.setUserId(user_id);
			em.persist(category);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Category> readCategory(String user_id) {
		EntityManager em = getEntityManager();
		try {
			return em.createQuery("SELECT c FROM Category c WHERE c.userId = :user_id", Category.class)
					.setParameter("user_id", user_id).getResultList();
		} finally {
			em.close();
		}
	}
	

	@Override
	public void updateCategory(String category_id, String name, String description, String user_id) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Category category = em.find(Category.class, category_id);
			if (category != null && category.getUserId().equals(user_id)) {
				category.setName(name);
				category.setDescription(description);
				em.merge(category);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteCategory(String category_id) {
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Category category = em.find(Category.class, category_id);
			if (category != null) {
				em.remove(category);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public String getID(String username) {
		EntityManager em = getEntityManager();
		try {
			return em.createQuery("SELECT u.id FROM User u WHERE u.userName = :username", Integer.class)
					.setParameter("username", username).getSingleResult().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean checkExistEmail(String email) {
		EntityManager em = getEntityManager();
		try {
			Long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
					.setParameter("email", email).getSingleResult();
			return count > 0;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean checkExistUsername(String username) {
		EntityManager em = getEntityManager();
		try {
			Long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.userName = :username", Long.class)
					.setParameter("username", username).getSingleResult();
			return count > 0;
		} finally {
			em.close();
		}
	}

	@Override
	public boolean checkExistPhone(String phone) {
		EntityManager em = getEntityManager();
		try {
			Long count = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.phone = :phone", Long.class)
					.setParameter("phone", phone).getSingleResult();
			return count > 0;
		} finally {
			em.close();
		}
	}
}
