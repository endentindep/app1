package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.*;
import jm.task.core.jdbc.model.User;

import java.io.IOException;
import java.util.List;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public void createUsersTable() {
		userDao.createUsersTable();
	}

	public UserServiceImpl() {
		userDao = new UserDaoHibernateImpl();
	}

	public void dropUsersTable() {
		userDao.dropUsersTable();
	}

	public void saveUser(String name, String lastName, byte age) {
		userDao.saveUser(name, lastName, age);
	}

	public void removeUserById(long id) {
		userDao.removeUserById(id);
	}

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public void cleanUsersTable() {
		userDao.cleanUsersTable();
	}

	public void close() throws IOException {
		userDao.close();
	}
}
