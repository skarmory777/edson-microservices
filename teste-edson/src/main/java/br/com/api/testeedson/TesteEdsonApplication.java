package br.com.api.testeedson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TesteEdsonApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteEdsonApplication.class, args);
		System.out.print(new BCryptPasswordEncoder().encode("123"));
	}
	
	@GetMapping("/")
	public String index() {		
		return "Olá Mundo!";
	}

}
