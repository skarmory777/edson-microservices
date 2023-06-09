package br.com.api.testeedson;
	
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
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
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.api.testeedson.dtos.AvailabilityDto;
import br.com.api.testeedson.dtos.CandidateDto;
import br.com.api.testeedson.dtos.ExamDto;
import br.com.api.testeedson.dtos.RoomDto;
import br.com.api.testeedson.services.RoomService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class AvailabilityControllerTest {

	String user = "admin";
	String pwd = "123";
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	RoomService roomService;
		
	@Autowired
	private ObjectMapper objectMapper;	
	
	@Test
	public void SchedulingOK() throws Exception {
		
		String resultString;
		ResultActions result;
		JacksonJsonParser jsonParser = new JacksonJsonParser();		
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		

		var roomDto = new RoomDto();
		roomDto.setName("Room - Teste Unitário");

		result = mockMvc.perform(post("/room")
					.with(csrf().asHeader())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(roomDto))
					.header("Authorization", "Basic " + encoding)).andExpect(
							status().isCreated());
		
	    resultString = result.andReturn().getResponse().getContentAsString();	    
	    
	    UUID roomId = UUID.fromString( jsonParser.parseMap(resultString).get("id").toString() );
	    
		var examDto = new ExamDto();
		examDto.setSubject("Exam - Teste Unitário");

		result = mockMvc.perform(post("/room")
						.with(csrf().asHeader())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(roomDto))
						.header("Authorization", "Basic " + encoding)).andExpect(
								status().isCreated());
		
	    resultString = result.andReturn().getResponse().getContentAsString();	
	    
	    UUID examId = UUID.fromString( jsonParser.parseMap(resultString).get("id").toString());
	    
	    
		LocalDate birthDate = LocalDate.parse("1987-10-01");		
		Date date1 = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		var candidateDto = new CandidateDto();
		candidateDto.setName("Candidate - Teste Unitário");
		candidateDto.setBirthDate(date1);
		candidateDto.setCpf("41707204071");						

		result = mockMvc.perform(post("/candidate")
				.with(csrf().asHeader())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(candidateDto))
				.header("Authorization", "Basic " + encoding)).andExpect(status().isCreated());	    
		
	    resultString = result.andReturn().getResponse().getContentAsString();	
	    
	    UUID candidateId = UUID.fromString( jsonParser.parseMap(resultString).get("id").toString() );		
			
		var availabilityDto = new AvailabilityDto();
		availabilityDto.setAvailabilityDate(date1);		
		availabilityDto.setCandidateId(candidateId);
		availabilityDto.setExamId(examId);
		availabilityDto.setRoomId(roomId);		

		mockMvc.perform(post("/availability")
						.with(csrf().asHeader())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(availabilityDto))
						.header("Authorization", "Basic " + encoding)).andExpect(
								status().isCreated());	    	    
	    
	}

	@Test
	public void AvailabilityIsAlreadyInUsed() throws Exception {
		String resultString;
		ResultActions result;
		JacksonJsonParser jsonParser = new JacksonJsonParser();		
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		

		var roomDto = new RoomDto();
		roomDto.setName("Room - Teste Unitário");

		result = mockMvc.perform(post("/room")
					.with(csrf().asHeader())
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(roomDto))
					.header("Authorization", "Basic " + encoding)).andExpect(
							status().isCreated());
		
	    resultString = result.andReturn().getResponse().getContentAsString();	    
	    
	    UUID roomId = UUID.fromString( jsonParser.parseMap(resultString).get("id").toString() );
	    
		var examDto = new ExamDto();
		examDto.setSubject("Exam - Teste Unitário");

		result = mockMvc.perform(post("/room")
						.with(csrf().asHeader())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(roomDto))
						.header("Authorization", "Basic " + encoding)).andExpect(
								status().isCreated());
		
	    resultString = result.andReturn().getResponse().getContentAsString();	
	    
	    UUID examId = UUID.fromString( jsonParser.parseMap(resultString).get("id").toString());
	    
	    
		LocalDate birthDate = LocalDate.parse("1987-10-01");		
		Date date1 = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		var candidateDto = new CandidateDto();
		candidateDto.setName("Candidate - Teste Unitário");
		candidateDto.setBirthDate(date1);
		candidateDto.setCpf("03424008001");						

		result = mockMvc.perform(post("/candidate")
				.with(csrf().asHeader())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(candidateDto))
				.header("Authorization", "Basic " + encoding)).andExpect(status().isCreated());	    
		
	    resultString = result.andReturn().getResponse().getContentAsString();	
	    
	    UUID candidateId = UUID.fromString( jsonParser.parseMap(resultString).get("id").toString() );		
			
		var availabilityDto = new AvailabilityDto();
		availabilityDto.setAvailabilityDate(date1);		
		availabilityDto.setCandidateId(candidateId);
		availabilityDto.setExamId(examId);
		availabilityDto.setRoomId(roomId);		

		mockMvc.perform(post("/availability")
						.with(csrf().asHeader())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(availabilityDto))
						.header("Authorization", "Basic " + encoding)).andExpect(
								status().isCreated());
		
		mockMvc.perform(post("/availability")
				.with(csrf().asHeader())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(availabilityDto))
				.header("Authorization", "Basic " + encoding)).andExpect(
						status().isCreated());
		
	}	
}
