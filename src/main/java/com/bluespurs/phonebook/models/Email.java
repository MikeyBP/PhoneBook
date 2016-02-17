package com.bluespurs.phonebook.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "emails")
@Entity
public class Email {
	@Id
	@GeneratedValue
	private int id;
	private String address;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Email(String address) {
		this.address = address;
	}
	
	//Creating a null constructor so this class is considered a Java Bean
	public Email() {		
	}
	

}
