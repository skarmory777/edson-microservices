package br.com.api.testeedson.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name="AVAILABILITY", uniqueConstraints={
	       @UniqueConstraint(name="UN_AVAILABILITY_EXAM_ID", columnNames={"EXAM_ID"}),
	       @UniqueConstraint(name="UN_AVAILABILITY_CANDIDATE_ID", columnNames={"CANDIDATE_ID"})
	   })
public class AvailabilityModel implements Serializable {

	private static final long serialVersionUID = 1L;
		
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;	
            
    @ManyToOne
    private RoomModel room;        
    
    @OneToOne
    private ExamModel exam;
    
    @OneToOne()
    private CandidateModel candidate;    

	@Column(nullable = false)
    private Date availabilityDate;	
	
    public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public RoomModel getRoom() {
		return room;
	}

	public void setRoom(RoomModel room) {
		this.room = room;
	}

	public ExamModel getExam() {
		return exam;
	}

	public void setExam(ExamModel exam) {
		this.exam = exam;
	}

	public CandidateModel getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateModel candidate) {
		this.candidate = candidate;
	}	
	
	public Date getAvailabilityDate() {
		return availabilityDate;
	}

	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}    
   
}
