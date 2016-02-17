package com.bluespurs.phonebook.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "phones")
@Entity
public class Phone {
	@Id
	@GeneratedValue
	private int id;
	private String number;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Phone(String number) {
		super();
		this.number = number;
	}
	
	//Creating a null constructor so this class is considered a Java Bean
	public Phone() {		
	}
	

}
