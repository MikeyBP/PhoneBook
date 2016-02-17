package com.bluespurs.phonebook.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "companies")
@Entity
public class Company {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Company(String name) {
		this.name = name;
	}
	
	//Creating a null constructor so this class is considered a Java Bean
	public Company() {		
	}
	

}
