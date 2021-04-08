package com.example.projetoitisenha.service.impl;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.projetoitisenha.service.PasswordService;

@Service
public class PasswordServiceImpl implements PasswordService{
	private static final String REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$@#!%^&*)(+-])(?=.*[0-9a-zA-Z])[0-9a-zA-Z$@#!%^&*)(+-]{9,}$";
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordServiceImpl.class);
	private String brokenPassword[];
	private int numberInteractions;
	
	@Override
	public Boolean isValid(String password) {
		LOGGER.info("Validando padrão da senha.");		
		Pattern pattern = Pattern.compile(REGEX);
		if(!password.isEmpty() && password != null && pattern.matcher(password).matches()) {
			this.brokenPassword = password.toUpperCase().split("");
			for(String letterPassword: brokenPassword) {
				numberInteractions = 0;
				for(int i = 0; i<brokenPassword.length;i++) {
					if(letterPassword.equals(brokenPassword[i])) {
						numberInteractions++;
					}
					if(numberInteractions>1) {
						return false;
					}
				}
			}
			
			return true;
		} 
		return false;
	}
	
	
}
	
