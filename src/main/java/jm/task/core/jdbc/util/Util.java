package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
	// реализуйте настройку соеденения с БД
	public static Connection connection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/users_db",
				"postgres",
				"1");
	}
}
