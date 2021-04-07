package com.example.projetoitisenha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetoitisenha.service.PasswordService;

@RestController
@RequestMapping("/validationPassword")
public class PasswordController {
	
	@Autowired
	private PasswordService senhaService;
	
	@GetMapping
	private boolean validation(@RequestParam(value = "password") String password) throws Exception {
			return senhaService.isValid(password);
	
	}
	@GetMapping("/{password}")
	private boolean validationByName(@PathVariable("password") String password) throws Exception {
			return senhaService.isValid(password);
	
	}
}
