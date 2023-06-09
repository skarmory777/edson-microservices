package br.com.api.testeedson.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoomDto {	
	
	@NotBlank
	@Size(max = 50)	
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
