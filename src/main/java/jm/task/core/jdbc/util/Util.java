package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import jm.task.core.jdbc.model.User;

public class Util {
	private static Metadata meta;

	// реализуйте настройку соеденения с БД
	public static Connection connection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/users_db",
				"postgres",
				"1");
	}

	public static SessionFactory getSessionFactory() {
		Map<String, String> settings = new HashMap<>();
		settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/users_db");
		settings.put("hibernate.connection.username", "postgres");
		settings.put("hibernate.connection.password", "1");
		settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
		meta = new MetadataSources(new StandardServiceRegistryBuilder().applySettings(settings).build())
				.addAnnotatedClass(User.class)
				.buildMetadata();
		return meta.buildSessionFactory();
	}

	public static Metadata getMetadata() {
		return meta;
	}
}
