package br.com.api.testeedson.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.api.testeedson.model.ExamModel;
import br.com.api.testeedson.repositories.ExamRepository;

@Service
public class ExamService {

	final ExamRepository examRepository;	
	
	public ExamService( ExamRepository examRepository) {
		this.examRepository = examRepository;
	}

	@Transactional
	public ExamModel save(ExamModel examModel) {
		// TODO Auto-generated method stub
		return examRepository.save(examModel);
	}		
	
	public List<ExamModel> findAll() {
		return examRepository.findAll();
	}

	public Optional<ExamModel> findById(UUID id) {		
		return examRepository.findById(id);
	}	
	
	@Transactional
	public void delete(ExamModel ExamModel) {
		examRepository.delete(ExamModel);		
	}
}
