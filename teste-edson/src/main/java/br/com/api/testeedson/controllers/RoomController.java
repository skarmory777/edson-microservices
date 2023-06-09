package br.com.api.testeedson.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.testeedson.services.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import br.com.api.testeedson.dtos.RoomDto;
import br.com.api.testeedson.model.RoomModel;


import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/room")
@Api(value="Salas")
public class RoomController {
	
	final RoomService roomService;

	public RoomController(RoomService roomService) {		
		this.roomService = roomService;
	}
	
    @PostMapping
    @ApiOperation(value = "Cadastrar uma sala")
    public ResponseEntity<Object> saveRoom(@RequestBody @Valid RoomDto roomDto){

        var roomModel = new RoomModel();        
        BeanUtils.copyProperties(roomDto, roomModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(roomModel));
    }
    
    @GetMapping
    @ApiOperation(value = "Retornar todas salas")
    public ResponseEntity<List<RoomModel>> getAllRooms(){
    	return ResponseEntity.status(HttpStatus.OK).body(roomService.findAll()); 
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retornar uma sala")
    public ResponseEntity<Object> getOneRoom(@PathVariable(value = "id") UUID id){
        Optional<RoomModel> roomModelOptional = roomService.findById(id);
        if (!roomModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(roomModelOptional.get());
    }   
    
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletar uma sala")
    public ResponseEntity<Object> deleteRoom(@PathVariable(value = "id") UUID id){
        Optional<RoomModel> roomModelOptional = roomService.findById(id);
        if (!roomModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        roomService.delete(roomModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Room deleted successfully.");
    }
    
    @PutMapping("/{id}")
    @ApiOperation(value = "Editar uma sala")
    public ResponseEntity<Object> updateRoom(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid RoomDto roomDto){
        Optional<RoomModel> roomModelOptional = roomService.findById(id);
        if (!roomModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found.");
        }
        var roomModel = new RoomModel();
        BeanUtils.copyProperties(roomDto, roomModel);
        roomModel.setId(roomModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(roomService.save(roomModel));
    }    
}
