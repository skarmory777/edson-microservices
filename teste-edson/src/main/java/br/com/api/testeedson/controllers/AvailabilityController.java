package br.com.api.testeedson.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.testeedson.dtos.AvailabilityDto;
import br.com.api.testeedson.model.ExamModel;
import br.com.api.testeedson.model.RoomModel;
import br.com.api.testeedson.model.AvailabilityModel;
import br.com.api.testeedson.model.CandidateModel;
import br.com.api.testeedson.services.ExamService;
import br.com.api.testeedson.services.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import br.com.api.testeedson.services.AvailabilityService;
import br.com.api.testeedson.services.CandidateService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/availability")
@Api(value="Agendamentos")
public class AvailabilityController {	
	
	final AvailabilityService availabilityService;	

	public AvailabilityController(AvailabilityService availabilityService) {		
		this.availabilityService = availabilityService;
	}
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private CandidateService candidateService;	
		
		
    @PostMapping
    @ApiOperation(value = "Cadastrar um agendamento")
    public ResponseEntity<Object> saveAvailability(@RequestBody @Valid AvailabilityDto availabilityDto){
        Optional<ExamModel> examModelOptional = examService.findById(availabilityDto.getExamId());
        if (!examModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exam not found.");
        }    	
        Optional<RoomModel> roomModelOptional = roomService.findById(availabilityDto.getRoomId());
        if (!roomModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        Optional<CandidateModel> candidateModelOptional = candidateService.findById(availabilityDto.getCandidateId());
        if (!candidateModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found.");
        }          
        if(availabilityService.existsByExamId(availabilityDto.getExamId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Exam is already in use!");
        }        
        if(availabilityService.existsByCandidateId(availabilityDto.getCandidateId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Candidate is already in use!");
        }        
        
        var availabilityModel = new AvailabilityModel();        
        availabilityModel.setExam(examModelOptional.get());
        availabilityModel.setRoom(roomModelOptional.get());
        availabilityModel.setCandidate(candidateModelOptional.get());
        BeanUtils.copyProperties(availabilityDto, availabilityModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(availabilityService.save(availabilityModel));
    }
    
    @GetMapping
    @ApiOperation(value = "Retornar todos os agendamentos")
    public ResponseEntity<List<AvailabilityModel>> getAllAvailabilitys(){
    	return ResponseEntity.status(HttpStatus.OK).body(availabilityService.findAll()); 
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retornar um agendamento")
    public ResponseEntity<Object> getOneAvailability(@PathVariable(value = "id") UUID id){
        Optional<AvailabilityModel> availabilityModelOptional = availabilityService.findById(id);
        if (!availabilityModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Availability not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(availabilityModelOptional.get());
    }   
    
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletar um agendamento")
    public ResponseEntity<Object> deleteAvailability(@PathVariable(value = "id") UUID id){
        Optional<AvailabilityModel> availabilityModelOptional = availabilityService.findById(id);
        if (!availabilityModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Availability not found.");
        }
        availabilityService.delete(availabilityModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Availability deleted successfully.");
    }
    
    @PutMapping("/{id}")
    @ApiOperation(value = "Editar um agendamento")
    public ResponseEntity<Object> updateAvailability(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid AvailabilityDto availabilityDto){
        Optional<AvailabilityModel> availabilityModelOptional = availabilityService.findById(id);
        if (!availabilityModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Availability not found.");
        }
        Optional<ExamModel> examModelOptional = examService.findById(availabilityDto.getExamId());
        if (!examModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exam not found.");
        }
        Optional<RoomModel> roomModelOptional = roomService.findById(availabilityDto.getRoomId());
        if (!roomModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        } 
        Optional<CandidateModel> candidateModelOptional = candidateService.findById(availabilityDto.getCandidateId());
        if (!candidateModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found.");
        }  
        if(availabilityService.existsByExamId(availabilityDto.getExamId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Exam is already in use!");
        }        
        if(availabilityService.existsByCandidateId(availabilityDto.getCandidateId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Candidate is already in use!");
        }        
        
        var availabilityModel = new AvailabilityModel();
        availabilityModel.setExam(examModelOptional.get());
        availabilityModel.setRoom(roomModelOptional.get());
        availabilityModel.setCandidate(candidateModelOptional.get());
        BeanUtils.copyProperties(availabilityDto, availabilityModel);
        availabilityModel.setId(availabilityModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(availabilityService.save(availabilityModel));
    }	 	
}
