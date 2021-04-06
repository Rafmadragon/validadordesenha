package com.example.projetoitisenha.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetoitisenha.service.SenhaService;

@RestController
public class SenhaController {
	private static final Logger logger = LoggerFactory.getLogger(SenhaController.class);
	
	@Autowired
	private SenhaService senhaService;
	
	@GetMapping("/validarSenha")
	public boolean senha(@RequestParam(value="senha") String senha) throws Exception {
			return senhaService.isValid(senha);
	
	}
	
}
