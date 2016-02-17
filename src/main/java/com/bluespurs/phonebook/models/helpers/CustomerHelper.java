package com.bluespurs.phonebook.models.helpers;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.ui.Model;

import com.bluespurs.phonebook.models.Company;
import com.bluespurs.phonebook.models.Customer;
import com.bluespurs.phonebook.models.Email;
import com.bluespurs.phonebook.models.Phone;

public class CustomerHelper {
	
	

	public static List<Customer> getCustomers() {
		Query query = PersistenceHelper.getHibernateSession().createQuery("from Customer");
		@SuppressWarnings("unchecked")
		List<Customer> list = query.list();
		return list;
	}

	public static Customer getCustomer(int customerId) {
		Query query = PersistenceHelper.getHibernateSession().createQuery("from Customer c where c.id =" + customerId);
		Customer customer = (Customer) query.uniqueResult();
		return customer;
	}

	public static List<Customer> searchCustomer(String criteria) {
				Query query = PersistenceHelper.getHibernateSession().createQuery("from Customer c where c.firstName like '%" +
																criteria + "%' or c.lastName like '%" + criteria +"%'");		
				@SuppressWarnings("unchecked")
				List<Customer> list = query.list();
				return list;
	}
	
	public static void removeCustomer(int customerId) {
		Session session = PersistenceHelper.getHibernateSession();
		Query query = session.createQuery("from Customer c WHERE c.id =" + customerId);
		Customer customer = (Customer) query.uniqueResult();
		if(customer == null){
			return;
		}
		Transaction transaction = session.beginTransaction();
		session.delete(customer);
		transaction.commit();
		session.close();
	}
	
	public static void modifyCustomer(Model model, int customerId){
		String firstName = (String) model.asMap().get("firstName");
		String lastName = (String) model.asMap().get("lastName");
		String phone = (String) model.asMap().get("phone");
		String email = (String) model.asMap().get("email");
		String company = (String) model.asMap().get("company");
		
				
		Session session = PersistenceHelper.getHibernateSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from Customer c WHERE c.id =" + customerId);
		Customer customer = (Customer) query.uniqueResult();
		if(customer == null){
			return;
		}
		
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		Phone phoneObject = new Phone(phone);
		session.persist(phoneObject);
		customer.modifyPhone(phoneObject);
		Company companyObject = new Company(company);
		session.persist(companyObject);
		customer.modifyCompany(companyObject);
		Email emailObject = new Email(email);
		session.persist(emailObject);
		customer.modifyEmail(emailObject);
		session.persist(customer);	
		transaction.commit();
		session.close();

	}

	public static void addCustomer(Model model) {
		String firstName = (String) model.asMap().get("firstName");
		String lastName = (String) model.asMap().get("lastName");
		String phone = (String) model.asMap().get("phone");
		String email = (String) model.asMap().get("email");
		String company = (String) model.asMap().get("company");


		Session session = PersistenceHelper.getHibernateSession();
		Transaction transaction = session.beginTransaction();
		Customer customer = new Customer(firstName, lastName);
		Company companyObject = new Company(company);
		session.persist(companyObject);
		customer.addCompany(companyObject);

		Email emailObject = new Email(email);
		session.persist(emailObject);
		customer.addEmail(emailObject);

		Phone phoneObject = new Phone(phone);
		session.persist(phoneObject);
		customer.addPhone(phoneObject);
		session.persist(customer);
		transaction.commit();
		session.close();
	}

}