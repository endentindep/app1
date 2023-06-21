package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

public class UserDaoHibernateImpl implements UserDao {
	private SessionFactory sessionFactory;
	private Session session;

	public UserDaoHibernateImpl() {
		sessionFactory = Util.getSessionFactory();
		session = sessionFactory.openSession();
	}

	@Override
	public void createUsersTable() {
		new SchemaExport().create(EnumSet.of(TargetType.DATABASE), Util.getMetadata());
	}

	@Override
	public void dropUsersTable() {
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.createSQLQuery("drop table if exists users").executeUpdate();
		transaction.commit();
	}

	@Override
	public void saveUser(String name, String lastName, byte age) {
		User user = new User(name, lastName, age);
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.persist(user);
		transaction.commit();
	}

	@Override
	public void removeUserById(long id) {
		User user = session.load(User.class, id);
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.delete(user);
		transaction.commit();
	}

	@Override
	public List<User> getAllUsers() {
		List<User> list = session.createQuery("select a from User a", User.class).getResultList();
		return list;
	}

	@Override
	public void cleanUsersTable() {
		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.createQuery("delete from User a").executeUpdate();
		transaction.commit();
	}

	@Override
	public void close() throws IOException {
		session.close();
	}
}
