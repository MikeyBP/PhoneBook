package com.bluespurs.phonebook.models;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name = "customers")
@Entity
public class Customer {
	@Id
	@GeneratedValue
	private int id;
	private String firstName;
	private String lastName;
    @ManyToMany
    private Set<Company> companies;
    @ManyToMany(cascade=CascadeType.ALL)
    private Set<Email> emails;
    @ManyToMany(cascade=CascadeType.ALL)
    private Set<Phone> phones;
    
    //Creating a null constructor so this class is considered a Java Bean
	public Customer(){		
		emails = new HashSet<Email>();
		phones = new HashSet<Phone>();
		companies = new HashSet<Company>();
	}

	public Customer(String firstName, String lastName) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
	}
    
    public void addCompany(Company company){
    	this.companies.add(company);
    }
	
	public void addEmail(Email email){
    	this.emails.add(email);
    }
    
    public void addPhone(Phone phone){
    	this.phones.add(phone);
    }
    
    public void modifyCompany(Company company){
    	//TODO: Make this work with multiple companies.
    	this.companies.clear();
    	this.companies.add(company);
    }
    
    public void modifyEmail(Email email){
    	//TODO: Make this work with multiple emails.
    	this.emails.clear();
    	this.emails.add(email);
    }  
        
    public void modifyPhone(Phone phone){
    	//TODO: Make this work with multiple phones.
    	this.phones.clear();
    	this.phones.add(phone);
    }

	public int getId() {
		return id;
	}

	public void SetId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Set<Email> getEmail() {
		return emails;
	}


	public void setEmail(Set<Email> emails) {
		this.emails = emails;
	}


	public Set<Phone> getPhone() {
		return phones;
	}


	public void setPhone(Set<Phone> phones) {
		this.phones = phones;
	}


	public Set<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(Set<Company> companies) {
		this.companies = companies;
	}	
}
