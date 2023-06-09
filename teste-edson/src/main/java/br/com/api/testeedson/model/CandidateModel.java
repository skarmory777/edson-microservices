package br.com.api.testeedson.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="CANDIDATE")
public class CandidateModel implements Serializable {
	private static final long serialVersionUID = 1L;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
	
    @Column(name="NAME", nullable = false, unique = true, length = 50)
    private String name;
    
    @Column(name="CPF", nullable = false, unique = true, length = 11)
    private String cpf;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birthDate;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}	
}
