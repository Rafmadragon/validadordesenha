package com.example.projetoitisenha.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PasswordEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String password;
	private boolean isValidPassword;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isValidPassword() {
		return isValidPassword;
	}
	public void setIsValidPassword(boolean isValidPassword) {
		this.isValidPassword = isValidPassword;
	}
	
}
