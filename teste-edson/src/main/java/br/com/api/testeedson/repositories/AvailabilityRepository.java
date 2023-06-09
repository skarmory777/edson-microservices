package br.com.api.testeedson.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.testeedson.model.AvailabilityModel;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityModel, UUID>{

	boolean existsByExamId(UUID examId);	
	boolean existsByCandidateId(UUID candidateId);
}