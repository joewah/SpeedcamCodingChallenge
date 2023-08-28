package com.example.speedcam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 * stores all information relevant for a speeding incident in the database
 * @author wahljo1
 *
 */
@Entity(name="SPEEDING_INCIDENT")
public class SpeedingIncidentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @ManyToOne
    @JoinColumn(name = "registry_no") // name of the foreign key column in the ChildEntity table
    private OwnerEntity parent;
	
	private int maxSpeed;
	
	private int speed;
	
	private int fineInCHF;
	
	private String photoLocation;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public OwnerEntity getParent() {
		return parent;
	}

	public void setParent(OwnerEntity parent) {
		this.parent = parent;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getPhotoLocation() {
		return photoLocation;
	}

	public void setPhotoLocation(String photoLocation) {
		this.photoLocation = photoLocation;
	}

	public int getFineInCHF() {
		return fineInCHF;
	}

	public void setFineInCHF(int fineInCHF) {
		this.fineInCHF = fineInCHF;
	}

	

	
	
	
}
