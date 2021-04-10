package com.example.projetoitisenha;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.projetoitisenha.entity.PasswordEntity;
import com.fasterxml.jackson.databind.ObjectMapper;



@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PasswordJavaApiIntegrationTest {
	
	private PasswordEntity passwordEntity;
	
	@LocalServerPort
    private int port;
	
	@Autowired
	private MockMvc mvc;

	@Test
	@Order(1)
	public void contextLoad() {
		assertNotNull(mvc);
	}
	
	@Test
	@Order(2)
	public void testAPiGETURISucess() throws Exception {
		List<String> passwordList = new ArrayList<String>();
		 passwordList.add("AbTp9!fok");
		 passwordList.add("AbT09!fGk");
		 passwordList.add("AbTw9!fhk");
		 passwordList.add("*bTw9!fhk");
		 passwordList.add("+bT(9!fhk");
		 passwordList.add("bT-w9!fhk");
		 passwordList.add("-T3w9!fhk");
		 passwordList.add("@T3w9!fhk");		 
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");
		 passwordList.add("+3wE9!fhk");
		 passwordList.add("&3wE9!fhk");
// casos de sucesso comentador que geram problemas na uri e url no request por GET
	 
//caracteres inrregulares de URI ou operadores de URL -  %^# - Bad request consultar rfc-3986 e rfc-11738(URLs) e rfc-2396(URIs)
//# - no começo da frase e irregular e no meio e espaço em branco e no final da frase é irregular 
//		 passwordList.add("#3wE9!fhk");
//		 passwordList.add("AbT*9!fo%");
//		 passwordList.add("AbT^9!zok");
//		 passwordList.add("%3wE9!fhk");
//		 passwordList.add("AbT#9!fZk");
		 
		for(String password: passwordList) {
			this.mvc.perform(get("/validationPassword/"+password)).andExpect(status().isOk());
		}
	}
	
	@Test
	@Order(3)
	public void testAPiGETParameterSucess() throws Exception {
		List<String> passwordList = new ArrayList<String>();
		 passwordList.add("AbTp9!fok"); 

		 passwordList.add("AbT09!fGk");
		 passwordList.add("AbTw9!fhk");
		 passwordList.add("*bTw9!fhk");
		 passwordList.add("bT-w9!fhk");
		 passwordList.add("-T3w9!fhk");
		 passwordList.add("@T3w9!fhk");		 
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");


// casos de sucesso comentador que geram problemas na uri e url	no request por GET	 
//caracteres inrregulares de URI ou operadores de URL -  %^#&+ - Bad request
// + - considerado como operador logico - URI não reconhece como caracter
//# e & -considerado com espaço em branco não reconhece como caracter
		 
//		 passwordList.add("#3wE9!fhk");
//		 passwordList.add("AbT*9!fo%");
//		 passwordList.add("AbT^9!zok");
//		 passwordList.add("%3wE9!fhk");
//		 passwordList.add("AbT#9!fZk");	
//		 passwordList.add("&3wE9!fhk");
//		 passwordList.add("+3wE9!fhk");	 
//		 passwordList.add("+bT(9!fhk");
		 
		for(String password: passwordList) {
			this.mvc.perform(get("/validationPassword?password="+password)).andExpect(status().isOk());
		}	
		
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Order(4)
	public void testClassPasswordControllerCRUDComplete() throws Exception {
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
		 passwordList.add("$3wE9!fhk");
		 passwordList.add("%3wE9!fhk");
		 passwordList.add("*3wE9!fhk");
		 passwordList.add("(3wE9!fhk");
		 passwordList.add(")3wE9!fhk");
		 passwordList.add("+3wE9!fhk");
		 passwordList.add("&3wE9!fhk");
		 passwordList.add("#3wE9!fhk");
		 
		long id= 1;
		this.passwordEntity = new PasswordEntity();
		for(String password :  passwordList) {
			passwordEntity.setId(id);
			passwordEntity.setPassword(password);
			
			this.mvc.perform(post("/validationPassword/password")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
	        		.content(asJsonString(passwordEntity)))
			.andExpect(status().isCreated());
			
			id++;
		}
		id = 4;
		this.passwordEntity.setId(id);
		this.passwordEntity.setPassword("#3wE9!fhk");
		PasswordEntity passwordEntitycomparation = this.passwordEntity;
		this.mvc.perform(post("/validationPassword/password")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
      		.content(asJsonString(passwordEntity)))
		.andExpect(status().isConflict()).andReturn().equals(passwordEntitycomparation);
		
		id = 4;
		this.passwordEntity.setId(id);
		this.passwordEntity.setPassword("#3wE9!fhk");
		passwordEntitycomparation = this.passwordEntity;
		this.mvc.perform(put("/validationPassword/password/v1/"+id)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
      		.content(asJsonString(passwordEntity)))
		.andExpect(status().isCreated()).andReturn().equals(passwordEntitycomparation);
		
		this.mvc.perform(get("/validationPassword/password/"+4)).andExpect(status().isOk()).andReturn().equals(passwordEntitycomparation);		
		
		id = 5;
		this.passwordEntity.setId(id);
		this.passwordEntity.setPassword("#3wE9!fhk");
		passwordEntitycomparation = this.passwordEntity;
		this.mvc.perform(put("/validationPassword/password/v1/"+1)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
      		.content(asJsonString(passwordEntity)))
		.andExpect(status().isNotAcceptable()).andReturn().equals(passwordEntitycomparation);
		
		this.mvc.perform(delete("/validationPassword/password/"+1)).andExpect(status().isAccepted());
		
		this.mvc.perform(delete("/validationPassword/password")).andExpect(status().isAccepted());
		
	}
	
	@Test
	@Order(5)
	public void testReturnPasswordStatusNotAceeptablePOST() throws Exception {
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
		 
		long id= 1;
		PasswordEntity passwordEntity = new PasswordEntity();
		for(String password :  passwordList) {
			passwordEntity.setId(id);
			passwordEntity.setPassword(password);
			
			this.mvc.perform(post("/validationPassword/password")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
	        		.content(asJsonString(passwordEntity)))
			.andExpect(status().isNotAcceptable());
			
			id++;
		}
		
	}
	
	@Test
	@Order(6)
	public void testReturnPasswordGETNotFound() throws Exception {
		this.mvc.perform(get("/validationPassword/password/1")).andExpect(status().isNotFound());	
		
	}
	
	@Test
	@Order(7)
	public void testReturnPasswordPUTNotFound() throws Exception {
		PasswordEntity obj = new PasswordEntity();
		long id = 1;
		obj.setId(id);
		obj.setPassword("#3wE9!fhk");
		
		this.mvc.perform(put("/validationPassword/password/v1/"+id)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
        		.content(asJsonString(obj)))
		.andExpect(status().isNotFound());
		
	}
	@Test
	@Order(8)
	public void testReturnPasswordDeleteNotFound() throws Exception {
		PasswordEntity passwordEntity = new PasswordEntity();
		long id = 1;
		passwordEntity.setId(id);
		passwordEntity.setPassword("#3wE9!fhk");
		
		this.mvc.perform(delete("/validationPassword/password/1")).andExpect(status().isNotFound());
		
	}
	@Test
	@Order(9)
	public void testReturnPasswordDeleteAllNotFound() throws Exception {
		PasswordEntity passwordEntity = new PasswordEntity();
		long id = 1;
		passwordEntity.setId(id);
		passwordEntity.setPassword("#3wE9!fhk");
		
		this.mvc.perform(delete("/validationPassword/password")).andExpect(status().isNotFound());
		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
}
