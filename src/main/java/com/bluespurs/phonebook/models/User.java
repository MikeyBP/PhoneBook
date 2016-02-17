package com.bluespurs.phonebook.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name = "users")
@Entity
public class User {
	@Id
	@GeneratedValue
	private int id;
	@Column(unique=true, nullable=false) 
	private String username;
	private String password;	
	private String firstName;
	private String lastName;
    @ManyToMany
    private Set<Role> roles;
    private boolean enabled = true;

    
    //Creating a null constructor so this class is considered a Java Bean
	public User(){
		roles = new HashSet<Role>();
		enabled = true;
	}	

	public User(String username, String password, String firstName, String lastName) {
		this();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    
    public void addRole(Role role){
    	roles.clear();
    	this.roles.add(role);
    }
    
    public void modifyRole(Role role){
    	//TODO: Make this work with multiple companies.
    	this.roles.clear();
    	this.roles.add(role);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
