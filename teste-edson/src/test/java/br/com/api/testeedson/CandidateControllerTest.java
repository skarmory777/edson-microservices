package br.com.api.testeedson;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.testeedson.dtos.CandidateDto;

@SpringBootTest
@AutoConfigureMockMvc
public class CandidateControllerTest {
	
	@Autowired
	MockMvc mockMvc;	
		
	private ObjectMapper objectMapper;
	@Test
	public void CandidateTestGetAll() throws Exception {
		
		mockMvc.perform(get("/candidate")).andExpect(status().isOk());
			
	}
	
	@Test
	public void CandidateTestPost() throws Exception {
		
		
		LocalDate birthDate = LocalDate.parse("1987-10-01");
		
		Date date1 = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		var candidateDto = new CandidateDto();
		candidateDto.setName("Edson");
		candidateDto.setBirthDate(date1);
		candidateDto.setCpf("02084039061");				
		
		mockMvc.perform(post("/candidate").
				contentType("application/json").
				content(objectMapper.writeValueAsString(candidateDto))).andExpect(status().isOk());			
	}	
	

}
