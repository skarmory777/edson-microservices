package br.com.api.testeedson.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.testeedson.model.CandidateModel;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateModel, UUID>{
	
	List<CandidateModel> findByCpf(String cpf);

}
