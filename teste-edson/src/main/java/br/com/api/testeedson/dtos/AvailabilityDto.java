package br.com.api.testeedson.dtos;

import java.util.Date;
import java.util.UUID;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class AvailabilityDto {
	
	@NotNull		
	private Date availabilityDate;

	@NotNull
    @ManyToOne
    @JoinColumn(name="room_id", nullable=false)	
	private UUID roomId;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name="exam_id", nullable=false)	
	private UUID examId;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name="candidate_id", nullable=false)	
	private UUID candidateId;	
	
	public Date getAvailabilityDate() {
		return availabilityDate;
	}

	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	public UUID getRoomId() {
		return roomId;
	}

	public void setRoomId(UUID roomId) {
		this.roomId = roomId;
	}

	public UUID getExamId() {
		return examId;
	}

	public void setExamId(UUID examId) {
		this.examId = examId;
	}

	public UUID getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(UUID candidateId) {
		this.candidateId = candidateId;
	}	
}
