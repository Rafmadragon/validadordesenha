package com.example.projetoitisenha.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetoitisenha.model.Password;
import com.example.projetoitisenha.service.PasswordService;

@RestController
@RequestMapping("/validationPassword")
public class PasswordController {
	@Autowired
	private PasswordService passwordService;
	
	@GetMapping
	private boolean validation(@RequestParam(value = "password") String password) throws Exception {
			return passwordService.isValid(password);
	
	}
	@GetMapping("/{password}")
	private boolean validationByName(@PathVariable String password) throws Exception {
			return passwordService.isValid(password);
	
	}
	@PostMapping("/add")
	public Boolean add(@RequestBody Password password) {
		return passwordService.isValid(password.getPassword());
	}

}
