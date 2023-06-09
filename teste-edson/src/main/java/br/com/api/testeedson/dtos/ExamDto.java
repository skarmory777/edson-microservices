package br.com.api.testeedson.dtos;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class ExamDto {
	
	@NotBlank
	@Size(max = 50)	
    private String subject;
			
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
