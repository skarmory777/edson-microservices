package br.com.api.testeedson;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.testeedson.dtos.CandidateDto;
import br.com.api.testeedson.services.CandidateService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CandidateControllerTest {

	private String user = "admin";
	private String pwd = "123";
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	CandidateService candidateService;
		
	@Autowired
	private ObjectMapper objectMapper;
	@Test
	public void CandidateGetAllTest() throws Exception {
		
		mockMvc.perform(get("/candidate")).andExpect(status().isOk());
			
	}
	
	@Test
	public void CandidatePostTest() throws Exception {
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		
		LocalDate birthDate = LocalDate.parse("1987-10-01");
		
		Date date1 = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		var candidateDto = new CandidateDto();
		candidateDto.setName("Edson");
		candidateDto.setBirthDate(date1);
		candidateDto.setCpf("02084039061");						

		mockMvc.perform(post("/candidate")
				.with(csrf().asHeader())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(candidateDto))
				.header("Authorization", "Basic " + encoding)).andExpect(status().isCreated());			
	}	

	@Test		
	public void CandidatePutTest() throws Exception {

		UUID id = candidateService.findByCpf("02084039061");		
		
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		
		LocalDate birthDate = LocalDate.parse("1987-10-01");
		
		Date date1 = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		var candidateDto = new CandidateDto();
		candidateDto.setName("Teste Unitário");
		candidateDto.setBirthDate(date1);
		candidateDto.setCpf("02084039061");				

		mockMvc.perform(put("/candidate/"+ id.toString())
				.with(csrf().asHeader())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(candidateDto))
				.header("Authorization", "Basic " + encoding)).andExpect(status().isOk());			
	}	
	
	@Test		
	public void CandidateDeleteTest() throws Exception {

		UUID id = candidateService.findByCpf("02084039061");		
		
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		

		mockMvc.perform(delete("/candidate/"+ id.toString())
				.with(csrf().asHeader())
				.contentType("application/json")
				.header("Authorization", "Basic " + encoding)).andExpect(status().isOk());			
	}	

}
