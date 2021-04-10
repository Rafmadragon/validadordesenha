package com.example.projetoitisenha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.example.projetoitisenha.service.PasswordService;
import com.example.projetoitisenha.service.impl.PasswordServiceImpl;

@SpringBootTest (classes = {PasswordServiceImpl.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
public class PasswordJavaApiUnitTests {
	 @Autowired
	 PasswordService passwordService;
	 
	 @Test
	 public void validationSucessPasswordGET() throws Exception{
		 List<String> passwordList = new ArrayList<String>();
		 passwordList.add("AbTp9!fok"); 
		 passwordList.add("AbT*9!fo%");
		 passwordList.add("AbT^9!zok");
		 passwordList.add("AbT#9!fZk");
		 passwordList.add("AbT09!fGk");
		 passwordList.add("AbTw9!fhk");
		 passwordList.add("*bTw9!fhk");
		 passwordList.add("+bT(9!fhk");
		 passwordList.add("bT-w9!fhk");
		 passwordList.add("-T3w9!fhk");
		 passwordList.add("@T3w9!fhk");
		 passwordList.add("#T3w9!fhk");
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("%3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");
		 passwordList.add("+3wE9!fhk");
		 passwordList.add("&3wE9!fhk");
		 
		 boolean []booleansList = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
		 
		 for(int i = 0; i<passwordList.size(); i++) {
			 assertEquals(passwordService.isValid(passwordList.get(i)),booleansList[i]);
		 }
	 }
	 
	 @Test
	 public void validationPasswordNotAvailable() throws Exception{
		 List<String> passwordList = new ArrayList<String>();
		 passwordList.add("");
		 passwordList.add("aa");
		 passwordList.add("ab");
		 passwordList.add("AAAbbbCc");
		 passwordList.add("AbTp9!foo");
		 passwordList.add("AbTp9!foA");
		 passwordList.add("AbTp9 fok");
		 passwordList.add("-T-w9!fhk");
		 passwordList.add("         ");
		 passwordList.add("1234567890");
		 
		 boolean []booleansList = {false,false,false,false,false,false,false,false,false,false};
		 
		 for(int i = 0; i<passwordList.size(); i++) {
			 assertEquals(passwordService.isValid(passwordList.get(i)),booleansList[i]);
		 }
	 }

	 @Test
	 public void validationNegationPasswordNotAvailable() throws Exception{
		 List<String> passwordList = new ArrayList<String>();
		 passwordList.add("");
		 passwordList.add("aa");
		 passwordList.add("ab");
		 passwordList.add("AAAbbbCc");
		 passwordList.add("AbTp9!foo");
		 passwordList.add("AbTp9!foA");
		 passwordList.add("AbTp9 fok");
		 passwordList.add("AbTp9!fok");
		 passwordList.add("AbTp9!@@@");
		 passwordList.add("A$TA9!@@@");
		 passwordList.add("AbTA9!---");
		 passwordList.add("#bTA9!-++");
		 passwordList.add("Ab(A^!-++");
		 passwordList.add("AbK(01@%)");
		 
		 boolean []booleansList2 = {true,true,true,true,true,true,true,false,true,true,true,true,true,false};
		 
		 for(int i = 0; i<passwordList.size(); i++) {
			 assertNotEquals(passwordService.isValid(passwordList.get(i)),booleansList2[i]);
		 }
	 }
	 
	 @Test
	 public void validationNegationPasswordSucessGET() throws Exception{
		 List<String> passwordList = new ArrayList<String>();
		 passwordList.add("AbTp9!fok"); 
		 passwordList.add("AbT*9!fo%");
		 passwordList.add("AbT^9!zok");
		 passwordList.add("AbT#9!fZk");
		 passwordList.add("AbT09!fGk");
		 passwordList.add("AbTw9!fhk");
		 passwordList.add("*bTw9!fhk");
		 passwordList.add("+bT(9!fhk");
		 passwordList.add("bT-w9!fhk");
		 passwordList.add("-T3w9!fhk");
		 passwordList.add("@T3w9!fhk");
		 passwordList.add("#T3w9!fhk");
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("%3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");
		 passwordList.add("+3wE9!fhk");
		 passwordList.add("&3wE9!fhk");
		 
		 boolean []booleansList = {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
		 
		 for(int i = 0; i<passwordList.size(); i++) {
			 assertNotEquals(passwordService.isValid(passwordList.get(i)),booleansList[i]);
		 }
	 }
}
