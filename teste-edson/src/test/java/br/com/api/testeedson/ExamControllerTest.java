package br.com.api.testeedson;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.testeedson.dtos.ExamDto;
import br.com.api.testeedson.services.ExamService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ExamControllerTest {

	private String user = "admin";
	private String pwd = "123";
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ExamService examService;
		
	@Autowired
	private ObjectMapper objectMapper;
	@Test
	public void ExamGetAllTest() throws Exception {
		
		mockMvc.perform(get("/exam")).andExpect(status().isOk());
			
	}
	
	@Test
	public void ExamPostTest() throws Exception {
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		
		
		var examDto = new ExamDto();
		examDto.setSubject("Teste Unitário");

		mockMvc.perform(post("/exam")
				.with(csrf().asHeader())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(examDto))
				.header("Authorization", "Basic " + encoding)).andExpect(status().isCreated());			
	}	

	@Test		
	public void ExamPutTest() throws Exception {

		UUID id = examService.findAll().get(0).getId();		
		
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		
		
		var examDto = new ExamDto();
		examDto.setSubject("Teste Unitário");				

		mockMvc.perform(put("/exam/"+ id.toString())
				.with(csrf().asHeader())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(examDto))
				.header("Authorization", "Basic " + encoding)).andExpect(status().isOk());			
	}	
	
	@Test		
	public void ExamDeleteTest() throws Exception {

		UUID id = examService.findAll().get(0).getId();		
		
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		

		mockMvc.perform(delete("/exam/"+ id.toString())
				.with(csrf().asHeader())
				.contentType("application/json")
				.header("Authorization", "Basic " + encoding)).andExpect(status().isOk());			
	}
}