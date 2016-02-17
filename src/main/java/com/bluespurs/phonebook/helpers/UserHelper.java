package com.bluespurs.phonebook.helpers;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.ui.Model;

import com.bluespurs.phonebook.models.Role;
import com.bluespurs.phonebook.models.User;
import com.bluespurs.phonebook.models.helpers.PersistenceHelper;

public class UserHelper {
	//Prevent this class from being instantiated. All methods should be accessed in a static context
	private UserHelper(){		
	}
	
	public static boolean login(String username, String password){
		Session session = PersistenceHelper.getHibernateSession();
        Query query = session.createQuery("from User u WHERE u.username='" + username+ "' and password='" + PasswordHelper.hash(password)+"'" );
        @SuppressWarnings("unchecked")
			List<User> list = query.list();
        session.close();
		return list.size() != 0;
	}
	
	public static boolean isAdmin(String username){
		Session session = PersistenceHelper.getHibernateSession();
	    Query query = session.createQuery("from User u WHERE u.username='" + username + "'" );
	    User user = (User)query.uniqueResult();
		if(user == null){
		   	session.close();
		   	return false;
		}        	
		for(Role role: user.getRoles()){
		   	if(role.getName().equalsIgnoreCase("administrator")){
		   		session.close();
		   		return true;
		   	}        
		}
		session.close();
		return false;
	}
	
	public static List<User> getUsers() {
		Query query = PersistenceHelper.getHibernateSession().createQuery("from User");
		@SuppressWarnings("unchecked")
		List<User> list = query.list();
		return list;
	}

	public static User getUser(int userId) {
		Query query = PersistenceHelper.getHibernateSession().createQuery("from User u where u.id =" + userId);
		User user = (User) query.uniqueResult();
		return user;
	}

	public static List<User> searchUser(String criteria) {
				Query query = PersistenceHelper.getHibernateSession().createQuery("from User u where u.firstName like '%" +
																criteria + "%' or u.lastName like '%" + criteria +"%'" +
																"or u.username like '%" + criteria +"%'");		
				@SuppressWarnings("unchecked")
				List<User> list = query.list();
				return list;
	}
	
	public static void removeUser(int userId) {
		Session session = PersistenceHelper.getHibernateSession();
		Query query = session.createQuery("from User u WHERE u.id =" + userId);
		User user = (User) query.uniqueResult();
		if(user == null){
			return;
		}
		Transaction transaction = session.beginTransaction();
		session.delete(user);
		transaction.commit();
		session.close();
	}
	
	public static void modifyUser(Model model, int userId){
		String firstName = (String) model.asMap().get("firstName");
		String lastName = (String) model.asMap().get("lastName");
		String userName = (String) model.asMap().get("userName");
		String password = (String) model.asMap().get("password");
		int roleId = (int) model.asMap().get("roleId");
		boolean enabled = (boolean) model.asMap().get("enabled");
		
				
		Session session = PersistenceHelper.getHibernateSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from User u WHERE u.id =" + userId);
		User user = (User) query.uniqueResult();
		if(user == null){
			return;
		}
		
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(userName);
		user.setPassword(password);
		user.setEnabled(enabled);
		user.addRole(getRole(roleId));
		session.persist(user);	
		transaction.commit();
		session.close();

	}

	public static void addUser(Model model) {
		String firstName = (String) model.asMap().get("firstName");
		String lastName = (String) model.asMap().get("lastName");
		String userName = (String) model.asMap().get("userName");
		String password = (String) model.asMap().get("password");
		int roleId = (int) model.asMap().get("roleId");
		boolean enabled = (boolean) model.asMap().get("enabled");


		Session session = PersistenceHelper.getHibernateSession();
		Transaction transaction = session.beginTransaction();
		User user = new User(userName, password, firstName, lastName);
		user.setEnabled(enabled);
		user.addRole(getRole(roleId));
		session.persist(user);
		transaction.commit();
		session.close();
	}
	
	public static boolean isEnabled(String username){		
		Session session = PersistenceHelper.getHibernateSession();
	        Query query = session.createQuery("from User u WHERE u.username='" + username+ "' and enabled=1" );
	        User user = (User)query.uniqueResult();
	        if(user == null){
	        	session.close();
	        	return false;
	        }
			return true;
	}
	
	public static Role getRole(int roleId){
		Query query = PersistenceHelper.getHibernateSession().createQuery("from Role r where r.id =" + roleId);
		Role role = (Role) query.uniqueResult();
		return role;
	}

	public static List<Role> getRoles() {
		Query query = PersistenceHelper.getHibernateSession().createQuery("from Role");
		@SuppressWarnings("unchecked")
		List<Role> roles = query.list();
		return roles;
	}
	
}
