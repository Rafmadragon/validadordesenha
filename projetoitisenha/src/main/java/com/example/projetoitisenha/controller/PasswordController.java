package com.example.projetoitisenha.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private PasswordRepository passwordRepository;
	
	@GetMapping
	private  ResponseEntity<Boolean> validation(@RequestParam(value = "password") String password) throws Exception {
		boolean isPasswordIsValid =this.passwordService.isValid(password);
		return new ResponseEntity<>(isPasswordIsValid, HttpStatus.OK);

	}
	@GetMapping("/{password}")
	private ResponseEntity<Boolean> validationByName(@PathVariable String password) throws Exception {
		boolean isPasswordIsValid =this.passwordService.isValid(password);
		return new ResponseEntity<>(isPasswordIsValid, HttpStatus.OK);

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
		Optional<PasswordEntity> passwordValidation= this.passwordRepository.findById(passwordEntity.getId());
        if(passwordValidation.isPresent() != true){
			passwordEntity.setIsValidPassword(this.passwordService.isValid(passwordEntity.getPassword()));
			if(passwordEntity.isValidPassword()) {
				 this.passwordRepository.save(passwordEntity);
				 return new ResponseEntity<PasswordEntity>(passwordEntity, HttpStatus.CREATED);
			}
			 return new ResponseEntity<>(passwordEntity,HttpStatus.NOT_ACCEPTABLE);
        }
        else
        	return new ResponseEntity<>(passwordEntity,HttpStatus.CONFLICT);
    }
	
	@PutMapping(path = "/password/v1/{id}" , produces = { "application/json" })
    public ResponseEntity<PasswordEntity> Put(@PathVariable Long id, @Valid @RequestBody PasswordEntity newPasswordEntity)
    {	
        Optional<PasswordEntity> oldPasswordEntity = this.passwordRepository.findById(id);
        if(oldPasswordEntity.isPresent()){
        	newPasswordEntity.setIsValidPassword(this.passwordService.isValid(newPasswordEntity.getPassword()));
        	if(newPasswordEntity.isValidPassword() && id == newPasswordEntity.getId()) {
        		PasswordEntity passwordEntity = oldPasswordEntity.get();
            	passwordEntity.setPassword(newPasswordEntity.getPassword());
            	this.passwordRepository.save(passwordEntity);
                return new ResponseEntity<PasswordEntity>(passwordEntity, HttpStatus.CREATED);
        	}
        	else
        		return new ResponseEntity<>(newPasswordEntity,HttpStatus.NOT_ACCEPTABLE);        	
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
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	@DeleteMapping("/password")
    public ResponseEntity<Object> DeleteAll()
    {	
		if(this.passwordRepository.findAll().size()>0) {
			this.passwordRepository.deleteAll();
	        return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
    }
	
}
