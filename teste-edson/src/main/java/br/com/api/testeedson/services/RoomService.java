package br.com.api.testeedson.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.api.testeedson.model.RoomModel;
import br.com.api.testeedson.repositories.RoomRepository;

@Service
public class RoomService {

	final RoomRepository roomRepository;	
	
	public RoomService( RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Transactional
	public RoomModel save(RoomModel roomModel) {
		return roomRepository.save(roomModel);
	}		
	
	public boolean findByCpf(String cpf) {
		return true;
	}

	public List<RoomModel> findAll() {
		return roomRepository.findAll();
	}

	public Optional<RoomModel> findById(UUID id) {		
		return roomRepository.findById(id);
	}

	@Transactional
	public void delete(RoomModel roomModel) {
		roomRepository.delete(roomModel);		
	}
}
