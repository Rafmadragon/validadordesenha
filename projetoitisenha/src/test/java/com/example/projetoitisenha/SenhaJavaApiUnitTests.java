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

import com.example.projetoitisenha.service.SenhaService;

@SpringBootTest (classes = {SenhaService.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
public class SenhaJavaApiUnitTests {
	 @Autowired
	 SenhaService senhaService;
	 
	 @Test
	 public void validarSenhaComSucessoGET() throws Exception{
		 List<String> listaSenha = new ArrayList<String>();
		 listaSenha.add("AbTp9!fok"); 
		 listaSenha.add("AbT*9!fo%");
		 listaSenha.add("AbT^9!zok");
		 listaSenha.add("AbT#9!fZk");
		 listaSenha.add("AbT09!fGk");
		 listaSenha.add("AbTw9!fhk");
		 listaSenha.add("*bTw9!fhk");
		 listaSenha.add("+bT(9!fhk");
		 listaSenha.add("bT-w9!fhk");
		 listaSenha.add("-T3w9!fhk");
		 listaSenha.add("@T3w9!fhk");
		 listaSenha.add("#T3w9!fhk");
		 listaSenha.add("$3wE9!fhk");
		 listaSenha.add("%3wE9!fhk");
		 listaSenha.add("*3wE9!fhk");
		 listaSenha.add("(3wE9!fhk");
		 listaSenha.add(")3wE9!fhk");
		 listaSenha.add("+3wE9!fhk");
		 
		 boolean []listaBoolean = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
		 
		 for(int i = 0; i<listaSenha.size(); i++) {
			 assertEquals(senhaService.isValid(listaSenha.get(i)),listaBoolean[i]);
		 }
	 }
	 @Test
	 public void validarSenhaSemSucessoGET() throws Exception{
		 List<String> listaSenha = new ArrayList<String>();
		 listaSenha.add("");
		 listaSenha.add("aa");
		 listaSenha.add("ab");
		 listaSenha.add("AAAbbbCc");
		 listaSenha.add("AbTp9!foo");
		 listaSenha.add("AbTp9!foA");
		 listaSenha.add("AbTp9 fok");
		 listaSenha.add("-T-w9!fhk");
		 listaSenha.add("         ");
		 listaSenha.add("1234567890");
		 boolean []listaBoolean = {false,false,false,false,false,false,false,false,false,false};
		 
		 for(int i = 0; i<listaSenha.size(); i++) {
			 assertEquals(senhaService.isValid(listaSenha.get(i)),listaBoolean[i]);
		 }
	 }
	 
	 @Test
	 public void validarNegacaoValidadorDeSenhaGET() throws Exception{
		 List<String> listaSenha = new ArrayList<String>();
		 listaSenha.add("");
		 listaSenha.add("aa");
		 listaSenha.add("ab");
		 listaSenha.add("AAAbbbCc");
		 listaSenha.add("AbTp9!foo");
		 listaSenha.add("AbTp9!foA");
		 listaSenha.add("AbTp9 fok");
		 listaSenha.add("AbTp9!fok");
		 listaSenha.add("AbTp9!@@@");
		 listaSenha.add("A$TA9!@@@");
		 listaSenha.add("AbTA9!---");
		 listaSenha.add("#bTA9!-++");
		 listaSenha.add("Ab(A^!-++");
		 listaSenha.add("AbK(01@%)");
		 
		 boolean []listaBoolean2 = {true,true,true,true,true,true,true,false,true,true,true,true,true,false};
		 for(int i = 0; i<listaSenha.size(); i++) {
			 assertNotEquals(senhaService.isValid(listaSenha.get(i)),listaBoolean2[i]);
		 }
	 }
}
