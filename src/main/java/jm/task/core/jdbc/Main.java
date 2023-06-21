package jm.task.core.jdbc;

import java.util.List;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
	public static void main(String[] args) {
		try (UserService userService = new UserServiceImpl()) {
			userService.createUsersTable();
			userService.saveUser("Павел1", "Павел2", (byte) 25);
			userService.saveUser("Антон1", "Антон2", (byte) 35);
			userService.saveUser("Дмитрий1", "Дмитрий2", (byte) 45);
			List<User> list = userService.getAllUsers();
			for (User user : list) {
				System.out.println(user);
			}
			userService.cleanUsersTable();
			userService.dropUsersTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
