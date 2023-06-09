package br.com.api.testeedson.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.api.testeedson.model.CandidateModel;
import br.com.api.testeedson.repositories.CandidateRepository;

@Service
public class CandidateService {

	final CandidateRepository candidateRepository;	
	
	public CandidateService( CandidateRepository candidateRepository) {
		this.candidateRepository = candidateRepository;
	}

	@Transactional
	public CandidateModel save(CandidateModel candidateModel) {
		return candidateRepository.save(candidateModel);
	}		
	
	public UUID findByCpf(String cpf) {
		return candidateRepository.findByCpf(cpf).get(0).getId();		
	}

	public List<CandidateModel> findAll() {
		return candidateRepository.findAll();
	}

	public Optional<CandidateModel> findById(UUID id) {		
		return candidateRepository.findById(id);
	}	

	@Transactional
	public void delete(CandidateModel candidateModel) {
		candidateRepository.delete(candidateModel);		
	}
}
