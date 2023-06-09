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

import br.com.api.testeedson.dtos.RoomDto;
import br.com.api.testeedson.services.RoomService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class RoomControllerTest {

	private String user = "admin";
	private String pwd = "123";
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
	RoomService roomService;
		
	@Autowired
	private ObjectMapper objectMapper;
	@Test
	public void RoomGetAllTest() throws Exception {
		
		mockMvc.perform(get("/room")).andExpect(status().isOk());
			
	}
	
	@Test
	public void RoomPostTest() throws Exception {
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		
		
		var roomDto = new RoomDto();
		roomDto.setName("Teste Unitário");

		mockMvc.perform(post("/room")
				.with(csrf().asHeader())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(roomDto))
				.header("Authorization", "Basic " + encoding)).andExpect(status().isCreated());			
	}	

	@Test		
	public void RoomPutTest() throws Exception {

		UUID id = roomService.findAll().get(0).getId();		
		
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		
		
		var roomDto = new RoomDto();
		roomDto.setName("Teste Unitário");				

		mockMvc.perform(put("/room/"+ id.toString())
				.with(csrf().asHeader())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(roomDto))
				.header("Authorization", "Basic " + encoding)).andExpect(status().isOk());			
	}	
	
	@Test		
	public void RoomDeleteTest() throws Exception {

		UUID id = roomService.findAll().get(0).getId();		
		
		String encoding = Base64.getEncoder().encodeToString((user + ":" + pwd).getBytes());		

		mockMvc.perform(delete("/room/"+ id.toString())
				.with(csrf().asHeader())
				.contentType("application/json")
				.header("Authorization", "Basic " + encoding)).andExpect(status().isOk());			
	}	
}
