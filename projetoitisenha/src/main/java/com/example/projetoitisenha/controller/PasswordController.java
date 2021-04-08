package com.example.projetoitisenha.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetoitisenha.entity.PasswordEntity;
import com.example.projetoitisenha.repository.PasswordRepository;
import com.example.projetoitisenha.service.PasswordService;


@RestController
@RequestMapping("/validationPassword")
public class PasswordController {
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private PasswordRepository passwordRepository;
	
	@GetMapping
	private boolean validation(@RequestParam(value = "password") String password) throws Exception {
			return this.passwordService.isValid(password);
	
	}
	@GetMapping("/{password}")
	private boolean validationByName(@PathVariable String password) throws Exception {
			return this.passwordService.isValid(password);
	
	}
	
	@GetMapping("/password")
    public List<PasswordEntity> Get() {
        return this.passwordRepository.findAll();
    }

	@GetMapping("/password/{id}")
    public ResponseEntity<PasswordEntity> GetById(@PathVariable(value = "id") long id)
    {
        Optional<PasswordEntity> passwordEntity = this.passwordRepository.findById(id);
        if(passwordEntity.isPresent())
            return new ResponseEntity<PasswordEntity>(passwordEntity.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@PostMapping("/password")
    public ResponseEntity<PasswordEntity> Post(@Valid @RequestBody PasswordEntity passwordEntity)
    {	
		passwordEntity.setIsValidPassword(this.passwordService.isValid(passwordEntity.getPassword()));
		if(passwordEntity.isValidPassword()) {
			 this.passwordRepository.save(passwordEntity);
			 return new ResponseEntity<PasswordEntity>(passwordEntity, HttpStatus.OK);
		}
		 return new ResponseEntity<>(passwordEntity,HttpStatus.NOT_FOUND);
    }
	
	@PutMapping(path = "/password/v1/{id}" , produces = { "application/json" })
    public ResponseEntity<PasswordEntity> Put(@PathVariable Long id, @Valid @RequestBody PasswordEntity newPasswordEntity)
    {	
		newPasswordEntity.setIsValidPassword(this.passwordService.isValid(newPasswordEntity.getPassword()));
        Optional<PasswordEntity> oldPasswordEntity = this.passwordRepository.findById(id);
        if(oldPasswordEntity.isPresent() && newPasswordEntity.isValidPassword()){
        	PasswordEntity passwordEntity = oldPasswordEntity.get();
        	passwordEntity.setPassword(newPasswordEntity.getPassword());
        	this.passwordRepository.save(passwordEntity);
            return new ResponseEntity<PasswordEntity>(passwordEntity, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(newPasswordEntity,HttpStatus.NOT_FOUND);
    }
	
	@DeleteMapping("/password/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<PasswordEntity> passwordEntity = this.passwordRepository.findById(id);
        if(passwordEntity.isPresent()){
        	this.passwordRepository.delete(passwordEntity.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
}
