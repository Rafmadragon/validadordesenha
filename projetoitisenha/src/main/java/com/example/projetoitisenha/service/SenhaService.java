package com.example.projetoitisenha.service;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SenhaService {
	private static final String REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$@#!%^&*)(-+])(?=.*[0-9a-zA-Z])[0-9a-zA-Z$@#!%^&*)(-+]{9,}$";
	private static final Logger logger = LoggerFactory.getLogger(SenhaService.class);

	public boolean isValid(String senha) throws Exception{
		logger.info("Validando padrão da senha.");
		Pattern padrao = Pattern.compile(REGEX);
		if(!senha.isEmpty() && senha != null && padrao.matcher(senha).matches()) {
			String senhaQuebrada[] = senha.toUpperCase().split("");
			for(String letraSenha: senhaQuebrada) {
				int qtdMatchs = 0;
				for(int i = 0; i<senhaQuebrada.length;i++) {
					if(letraSenha.equals(senhaQuebrada[i])) {
						qtdMatchs++;
					}
					if(qtdMatchs>1) {
						return false;
					}
				}
			}
			
			return true;
		} 
		return false;
	}
}
