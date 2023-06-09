package br.com.api.testeedson.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.api.testeedson.model.AvailabilityModel;
import br.com.api.testeedson.repositories.AvailabilityRepository;

@Service
public class AvailabilityService {

	final AvailabilityRepository availabilityRepository;	
	
	public AvailabilityService( AvailabilityRepository availabilityRepository) {
		this.availabilityRepository = availabilityRepository;
	}

	public boolean existsByExamId(UUID examId) {
		return availabilityRepository.existsByExamId(examId);
	}
	
	public boolean existsByCandidateId(UUID candidateId) {
		return availabilityRepository.existsByCandidateId(candidateId);
	}	
	
	@Transactional
	public AvailabilityModel save(AvailabilityModel availabilityModel) {
		// TODO Auto-generated method stub
		return availabilityRepository.save(availabilityModel);
	}		
	
	public List<AvailabilityModel> findAll() {
		return availabilityRepository.findAll();
	}

	public Optional<AvailabilityModel> findById(UUID id) {		
		return availabilityRepository.findById(id);
	}

	@Transactional
	public void delete(AvailabilityModel AvailabilityModel) {
		availabilityRepository.delete(AvailabilityModel);		
	}
}
