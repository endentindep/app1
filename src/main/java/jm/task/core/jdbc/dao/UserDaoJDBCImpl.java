package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao, Closeable {
	private Connection connection;

	public UserDaoJDBCImpl() {
		try {
			connection = Util.connection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createUsersTable() {
		try (Statement stmtCreateTable = connection.createStatement()) {
			stmtCreateTable.execute(
					"CREATE TABLE IF NOT EXISTS Users (" +
							"id SERIAL PRIMARY KEY," +
							"name varchar(255)," +
							"lastName varchar(255)," +
							"age int" +
							");");

			stmtCreateTable.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dropUsersTable() {
		try (Statement stmt = connection.createStatement()) {
			stmt.execute("DROP TABLE IF EXISTS Users");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveUser(String name, String lastName, byte age) {
		String SQL = "INSERT INTO Users (name, lastname, age) VALUES(?,?,?)";
		try (PreparedStatement pstmt = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, name);
			pstmt.setString(2, lastName);
			pstmt.setByte(3, age);
			pstmt.execute();
			System.out.println("User с именем - " + name + " добавлен в базу данных");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeUserById(long id) {
		try (Statement stmt = connection.createStatement()) {
			stmt.execute("DELETE FROM Users WHERE id = " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> getAllUsers() {
		List<User> list = new ArrayList<>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
			while (rs.next()) {
				list.add(new User(rs.getString("name"), rs.getString("lastname"), rs.getByte("age")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void cleanUsersTable() {
		try (Statement stmt = connection.createStatement()) {
			stmt.execute("TRUNCATE TABLE Users");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
